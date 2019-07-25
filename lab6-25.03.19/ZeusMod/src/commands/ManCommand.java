package commands;

import server.CollectionManager;
import java.util.HashMap;

/**
 * Класс {@code ManCommand} переопределяет метод {@code execute ()}, чтобы показать описание команды.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class ManCommand extends AbstractCommand {

    private HashMap<String, AbstractCommand> commands;

    public ManCommand(CollectionManager manager, HashMap<String, AbstractCommand> commands) {
        super(manager);
        setDescription("Показывает руководство к команде.");
        this.commands = commands;
    }

    @Override
    public synchronized String execute(String arg) {
            if (commands.containsKey(arg)) return arg + " - " + commands.get(arg).getDescription();
            else return "Неправильный аргумент.";
    }
}