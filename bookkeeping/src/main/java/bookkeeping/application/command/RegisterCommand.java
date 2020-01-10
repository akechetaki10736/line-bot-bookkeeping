package bookkeeping.application.command;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
@CommandMapping(commandName="!register")
public class RegisterCommand extends CommandTemplate {

    @Override
    void execute(List<String> arguments) {
        //Do action here....
    }

}
