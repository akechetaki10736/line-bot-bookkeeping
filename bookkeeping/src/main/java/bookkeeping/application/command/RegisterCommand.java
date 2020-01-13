package bookkeeping.application.command;

import bookkeeping.application.entity.Member;
import bookkeeping.application.service.MemberService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Component
@Slf4j
@CommandMapping(commandName="!register")
public class RegisterCommand extends CommandTemplate {

    private LineMessagingClient lineMessagingClient;
    private MemberService memberService;

    @Autowired
    public RegisterCommand(LineMessagingClient lineMessagingClient, MemberService memberService) {
        this.lineMessagingClient = lineMessagingClient;
        this.memberService = memberService;
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
        lineMessagingClient.pushMessage(new PushMessage(UID, new TextMessage("Register completed")));
    }

}
