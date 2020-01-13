package bookkeeping.application.command;

import bookkeeping.application.entity.Bill;
import bookkeeping.application.service.BillService;
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
    private BillService billService;

    @Autowired
    public AccountingCommand(LineMessagingClient lineMessagingClient, BillService billService) {
        this.lineMessagingClient = lineMessagingClient;
        this.billService = billService;
    }

    @Override
    void execute(List<String> arguments) {
        log.warn("Start accounting");
        String UID = arguments.get(0);
        List<Message> directions = Arrays.asList(
                new TextMessage("請依照下列格式輸入帳目資料"),
                new TextMessage("項目,金額,備註,消費日期"),
                new TextMessage("請注意使用逗號「,」分隔不同項目"),
                new TextMessage("消費時間格式： yyyy-MM-dd")
        );
        lineMessagingClient.pushMessage(new PushMessage(UID, directions));

    }
}
