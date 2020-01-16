package bookkeeping.application.command;

import bookkeeping.application.messages.QuickMessageOfAccountingSupplier;
import bookkeeping.application.messages.QuickMessageOfFunctionsSupplier;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@CommandMapping(commandName = "!finish")
public class FinishCommand extends CommandTemplate {

    private LineMessagingClient lineMessagingClient;
    private QuickMessageOfFunctionsSupplier quickMessageOfFunctionsSupplier;

    @Autowired
    public FinishCommand(LineMessagingClient lineMessagingClient, QuickMessageOfFunctionsSupplier quickMessageOfFunctionsSupplier) {
        this.lineMessagingClient = lineMessagingClient;
        this.quickMessageOfFunctionsSupplier = quickMessageOfFunctionsSupplier;
    }

    @Override
    void execute(List<String> arguments) {
        String UID = arguments.get(0);
        CommandHandler.statusMap.put(UID, "!normal");
        List<Message> directions = new ArrayList<>();
        directions.add(new TextMessage("離開記帳模式"));
        directions.add(quickMessageOfFunctionsSupplier.get());
        lineMessagingClient.pushMessage(new PushMessage(UID, directions));
    }
}
