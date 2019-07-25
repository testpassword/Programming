package server.commands;

import java.util.HashMap;

/**
 * Класс {@code ManCommand} переопределяет метод {@code execute()}, чтобы показать описание команды.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class ManCommand extends AbstractCommand {

    private HashMap<String, AbstractCommand> commands;

    public ManCommand(HashMap<String, AbstractCommand> commands) {
        setDescription("Показывает руководство к команде.");
        this.commands = commands;
    }

    @Override
    public synchronized String execute(String[] args) {
            if (commands.containsKey(args[0])) return args[0] + " - " + commands.get(args[0]).getDescription();
            else return "Неправильный аргумент.";
    }
}