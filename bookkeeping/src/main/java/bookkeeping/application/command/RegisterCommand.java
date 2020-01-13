package bookkeeping.application.command;


import bookkeeping.application.entity.Member;
import bookkeeping.application.messages.QuickMessageSupplier;
import bookkeeping.application.service.MemberService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Component
@Slf4j
@CommandMapping(commandName="!register")
public class RegisterCommand extends CommandTemplate {

    private LineMessagingClient lineMessagingClient;
    private MemberService memberService;
    private QuickMessageSupplier quickMessageSupplier;

    @Autowired
    public RegisterCommand(LineMessagingClient lineMessagingClient, MemberService memberService, QuickMessageSupplier quickMessageSupplier) {
        this.lineMessagingClient = lineMessagingClient;
        this.memberService = memberService;
        this.quickMessageSupplier = quickMessageSupplier;
    }

    @Override
    void execute(List<String> arguments) {
        String UID = arguments.get(0);

        String displayName = "";
        try {
            displayName = lineMessagingClient.getProfile(UID).get().getDisplayName();
        } catch (InterruptedException | ExecutionException  e) {
            log.error(e.getMessage());
        }
        memberService.save(new Member(UID, displayName, Timestamp.valueOf(LocalDateTime.now())));
        List<Message> replyMessages = new ArrayList<>();
        replyMessages.addAll(Arrays.asList(new TextMessage("註冊成功"), new TextMessage("可以開始記帳了")));
        replyMessages.add(quickMessageSupplier.get());
        lineMessagingClient.pushMessage(new PushMessage(UID, replyMessages));

    }

}
