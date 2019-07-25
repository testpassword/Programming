package commands;

import server.CollectionManager;
import server.ServerConnection;
import java.util.HashMap;

/**
 * Класс {@code HelpCommand} переопределяет метод {@code execute ()} для отображения всех доступных команд
 * {@link AbstractCommand} в {@link ServerConnection}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class HelpCommand extends AbstractCommand {

    private HashMap<String, AbstractCommand> commands;

    public HelpCommand(CollectionManager manager, HashMap<String, AbstractCommand> commands) {
        super(manager);
        setDescription("Показывает список доступных команд.");
        this.commands = commands;
    }

    @Override
    public synchronized String execute() {
        return commands.keySet().toString() + "\nВведите 'man {command}' для получения справки по к команде.";
    }
}