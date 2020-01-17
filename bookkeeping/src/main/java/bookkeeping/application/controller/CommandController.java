package bookkeeping.application.controller;

import bookkeeping.application.accouting.AccountingHandler;
import bookkeeping.application.command.CommandHandler;
import bookkeeping.application.entity.Member;
import bookkeeping.application.messages.QuickMessageOfRegisterSupplier;
import bookkeeping.application.service.MemberService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

@Slf4j
@LineMessageHandler
@RestController
public class CommandController {
    private LineMessagingClient lineMessagingClient;
    private CommandHandler commandHandler;
    private AccountingHandler accountingHandler;
    private QuickMessageOfRegisterSupplier quickMessageOfRegisterSupplier;
    @Autowired
    public CommandController(LineMessagingClient lineMessagingClient, CommandHandler commandHandler, AccountingHandler accountingHandler, QuickMessageOfRegisterSupplier quickMessageOfRegisterSupplier) {
        this.lineMessagingClient = lineMessagingClient;
        this.commandHandler = commandHandler;
        this.accountingHandler = accountingHandler;
        this.quickMessageOfRegisterSupplier = quickMessageOfRegisterSupplier;
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        String userId = event.getSource().getUserId();
        if(message.getText().startsWith("!")) // It's a command
            commandHandler.execute(message.getText().split(" ")[0],userId + "," + message.getText());
//        if(CommandHandler.statusMap.get(userId).equals("!add"))
//            accountingHandler.addBill(userId, message.getText());

//        this.reply(event.getReplyToken(), new TextMessage(message.getText()));
    }

    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        String replyToken = event.getReplyToken();
        List<Message> greet = Arrays.asList(
                new TextMessage("歡迎使用記帳機器人"),
                new TextMessage("新朋友請先點擊註冊來啟用功能"),
                new TextMessage("系統訊息：查帳功能開發中，敬請期待"),
                quickMessageOfRegisterSupplier.get()
        );
        this.reply(replyToken, greet);
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

    @EventMapping
    public void handlePostbackEvent(PostbackEvent event){

    }
}
