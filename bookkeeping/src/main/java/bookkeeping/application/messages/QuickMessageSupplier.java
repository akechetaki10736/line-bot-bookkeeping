package bookkeeping.application.messages;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.quickreply.QuickReply;
import com.linecorp.bot.model.message.quickreply.QuickReplyItem;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Component
public class QuickMessageSupplier implements Supplier<Message> {
    @Override
    public Message get() {
        List<QuickReplyItem> messageOptions = Arrays.asList(
                QuickReplyItem.builder()
                        .action(new MessageAction("記帳", "!add"))
                        .build(),
                QuickReplyItem.builder()
                        .action(new MessageAction("查帳", "!query"))
                        .build()
        );

        final QuickReply quickReply = QuickReply.items(messageOptions);

        return TextMessage
                .builder()
                .text("請選擇要使用的功能")
                .quickReply(quickReply)
                .build();
    }
}
