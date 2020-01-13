package bookkeeping.application.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
@Slf4j
public class CommandHandler {
    public static Map<String, Consumer<List<String>>> commandMap = new HashMap<>();
    public Map<String, String> statusMap;
    public CommandHandler() {
        statusMap = new HashMap<>();
    }

    public void execute(String cmd, String arguments) {

        List<String> argList = new ArrayList<>();
        argList.addAll(Arrays.asList(arguments.split(",")));
        String userId = argList.get(0);

        if(!commandMap.containsKey(cmd))
            commandMap.get("Unknown").accept(argList);
        else {
            commandMap.get(cmd).accept(argList);
            statusMap.put(userId, cmd);
        }

    }
}
