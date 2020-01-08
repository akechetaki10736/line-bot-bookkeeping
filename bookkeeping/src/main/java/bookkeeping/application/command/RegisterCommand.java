package bookkeeping.application.command;

import org.springframework.stereotype.Component;

@Component
public class RegisterCommand extends CommandTemplate{

    public RegisterCommand() {
        CommandHandler.commandMap.put("!register", (String arguments) -> this.execute(arguments));
    }

    @Override
    void execute(String arguments) {
        //Do action here....
    }
}
