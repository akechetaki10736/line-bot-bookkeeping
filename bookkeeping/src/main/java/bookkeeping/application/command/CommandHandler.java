package bookkeeping.application.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
@Slf4j
public class CommandHandler {
    public static Map<String, Consumer<String>> commandMap;
    public CommandHandler() {
        commandMap = new HashMap<>();
    }
    public void execute(String cmd, String arguments) {
        if(!commandMap.containsKey(cmd))
            commandMap.get("Unknown").accept(arguments);
        else
            commandMap.get(cmd).accept(arguments);
    }
}
