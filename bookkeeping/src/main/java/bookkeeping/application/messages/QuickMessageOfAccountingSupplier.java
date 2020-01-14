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
public class QuickMessageOfAccountingSupplier implements Supplier<Message> {
    @Override
    public Message get() {
        List<QuickReplyItem> messageOptions = Arrays.asList(
                QuickReplyItem.builder()
                        .action(new MessageAction("完成記帳", "!finish"))
                        .build()
        );

        final QuickReply quickReply = QuickReply.items(messageOptions);

        return TextMessage
                .builder()
                .text("如欲離開記帳模式請選擇離開")
                .quickReply(quickReply)
                .build();
    }
}
