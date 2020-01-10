package bookkeeping.application.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
@Slf4j
public class CommandHandler {
    public static Map<String, Consumer<List<String>>> commandMap;
    public CommandHandler() {
        commandMap = new HashMap<>();
    }

    public void execute(String cmd, String arguments) {

        List<String> argList = new ArrayList<>();
        argList.addAll(Arrays.asList(arguments.split(",")));

        if(!commandMap.containsKey(cmd))
            commandMap.get("Unknown").accept(argList);
        else
            commandMap.get(cmd).accept(argList);
    }
}
