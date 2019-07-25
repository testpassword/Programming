package server.commands;

import server.Connection;
import server.managers.CollectionManager;
import tale.Shorty;
import java.util.List;

/**
 * Класс {@code InfoCommand} переопределяет метод {@code execute()} для отображения информации о {@link CollectionManager}.
 * @author Артемий Кульбако
 * @version 1.5
 * @since 20.05.19
 */
public class InfoCommand extends AbstractCommand {

    Connection currentConnection;

    public InfoCommand(Connection currentConnection)
    {
        this.currentConnection = currentConnection;
        setDescription("Выводит информацию о коллекции.");
    }

    @Override
    public synchronized String execute() {
        List<Shorty> collection = CollectionManager.getInstance().getCitizens();
        int yourElements = 0;
        for (Shorty s: collection) if (s.getMasterID() == currentConnection.getMasterID()) yourElements++;
        return CollectionManager.getInstance().toString() + "\nВаш номер в системе: " +
                currentConnection.getMasterID() + "\nКоличество ваших элементов: " + yourElements;
    }
}