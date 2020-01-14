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
public class QuickMessageOfRegisterSupplier implements Supplier<Message> {
    @Override
    public Message get() {
        List<QuickReplyItem> messageOptions = Arrays.asList(
                QuickReplyItem.builder()
                        .action(new MessageAction("註冊", "!register"))
                        .build()
        );

        final QuickReply quickReply = QuickReply.items(messageOptions);

        return TextMessage
                .builder()
                .text("請先註冊")
                .quickReply(quickReply)
                .build();
    }
}
