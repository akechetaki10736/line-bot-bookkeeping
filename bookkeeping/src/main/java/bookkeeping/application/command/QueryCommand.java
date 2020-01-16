package bookkeeping.application.command;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@CommandMapping(commandName = "!query")
public class QueryCommand extends CommandTemplate{

    private LineMessagingClient lineMessagingClient;

    @Value("${pageid}")
    private String pageid;

    @Autowired
    public QueryCommand(LineMessagingClient lineMessagingClient) {
        this.lineMessagingClient = lineMessagingClient;
    }

    @Override
    void execute(List<String> arguments) {
        String UID = arguments.get(0);
        lineMessagingClient.pushMessage(new PushMessage(UID, Arrays.asList(
                new TextMessage("Please click following link to query your bill"),
                new TextMessage("line://app/" + pageid)
        )));
    }
}
