package bookkeeping.application.controller;

import bookkeeping.application.entity.Member;
import bookkeeping.application.service.MemberService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

@Slf4j
@LineMessageHandler
@RestController
public class CommandController {
    private LineMessagingClient lineMessagingClient;
    private MemberService memberService;


    @Autowired
    public CommandController(LineMessagingClient lineMessagingClient, MemberService memberService) {
        this.lineMessagingClient = lineMessagingClient;
        this.memberService = memberService;
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        Member member = new Member(event.getSource().getUserId(), lineMessagingClient.getProfile(event.getSource().getUserId()).get().getDisplayName());
        memberService.save(member);
        this.reply(event.getReplyToken(), new TextMessage("echo : " + message.getText()));
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, singletonList(message));
    }
    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        reply(replyToken, messages, false);
    }
    private void reply(@NonNull String replyToken,
                       @NonNull List<Message> messages,
                       boolean notificationDisabled) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages, notificationDisabled))
                    .get();
            log.info("Sent messages: {}", apiResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
