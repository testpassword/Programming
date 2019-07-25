package server.commands;

import server.Connection;
import java.util.HashMap;

/**
 * Класс {@code HelpCommand} переопределяет метод {@code execute()} для отображения всех доступных команд
 * {@link AbstractCommand} в {@link Connection}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class HelpCommand extends AbstractCommand {

    private HashMap<String, AbstractCommand> commands;

    public HelpCommand(HashMap<String, AbstractCommand> commands) {
        setDescription("Показывает список доступных команд.");
        this.commands = commands;
    }

    @Override
    public synchronized String execute() {
        return commands.keySet().toString() + "\nВведите 'man {command}' для получения справки по к команде.";
    }
}