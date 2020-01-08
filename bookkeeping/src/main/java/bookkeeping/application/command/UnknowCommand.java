package bookkeeping.application.command;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UnknowCommand extends CommandTemplate {

    public UnknowCommand() {
        CommandHandler.commandMap.put("Unknown", (String arguments) -> this.execute(arguments));
    }
    @Override
    void execute(String arguments) {
        log.warn("Unknown command");
    }
}
