package bookkeeping.application.command;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@CommandMapping(commandName="Unknown")
public class UnknownCommand extends CommandTemplate {

    @Override
    void execute(List<String> arguments) {
        log.warn("Unknown command");
    }
}
