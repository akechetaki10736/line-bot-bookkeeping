package bookkeeping.application.command;

import bookkeeping.application.entity.Bill;
import bookkeeping.application.entity.Member;
import bookkeeping.application.messages.QuickMessageOfRegisterSupplier;
import bookkeeping.application.service.BillService;
import bookkeeping.application.service.MemberService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@CommandMapping(commandName = "!add")
public class AccountingCommand extends CommandTemplate {

    private LineMessagingClient lineMessagingClient;
    private MemberService memberService;
    private QuickMessageOfRegisterSupplier quickMessageOfRegisterSupplier;

    @Autowired
    public AccountingCommand(LineMessagingClient lineMessagingClient, MemberService memberService, QuickMessageOfRegisterSupplier quickMessageOfRegisterSupplier) {
        this.lineMessagingClient = lineMessagingClient;
        this.memberService = memberService;
        this.quickMessageOfRegisterSupplier = quickMessageOfRegisterSupplier;
    }

    @Override
    void execute(List<String> arguments) {
        log.warn("Start accounting");
        String UID = arguments.get(0);

        if(!memberService.findById(UID).isPresent()) {
            lineMessagingClient.pushMessage(new PushMessage(UID, quickMessageOfRegisterSupplier.get()));
            return;
        }

        List<Message> directions = Arrays.asList(
                new TextMessage("請依照下列格式輸入帳目資料"),
                new TextMessage("項目/金額/備註/消費日期"),
                new TextMessage("請注意使用逗號「/」分隔不同項目"),
                new TextMessage("若有不需要輸入的欄位，請留空，例如：食物/100//2019-01-04"),
                new TextMessage("消費時間格式： yyyy-MM-dd")
        );
        lineMessagingClient.pushMessage(new PushMessage(UID, directions));
        CommandHandler.statusMap.put(UID, "!add");
    }
}
