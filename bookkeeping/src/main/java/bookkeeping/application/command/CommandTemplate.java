package bookkeeping.application.command;

import java.util.List;

public abstract class CommandTemplate {
    public CommandTemplate() {

        String commandName = this.getClass()
                .getAnnotation(CommandMapping.class)
                .commandName();

        CommandHandler.commandMap.put(commandName, (arguments) -> this.execute(arguments));
    }
    abstract void execute(List<String> arguments);
}
