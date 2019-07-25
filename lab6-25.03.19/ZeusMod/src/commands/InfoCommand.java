package commands;

import server.CollectionManager;

/**
 * Класс {@code InfoCommand} переопределяет метод {@code execute ()} для отображения информации о {@link CollectionManager}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class InfoCommand extends AbstractCommand {

    public InfoCommand(CollectionManager manager) {
        super(manager);
        setDescription("Выводит информацию о коллекции.");
    }

    @Override
    public String execute(String arg) {
        return execute();
    }

    @Override
    public synchronized String execute() {
        return "Лабораторная 6 сделана Артемием Кульбако - VT ITMO 2019\n" + getManager().toString();
    }
}