package server.commands;

import server.Connection;
import server.managers.CollectionManager;
import tale.Shorty;
import java.util.List;
import java.util.ListIterator;

/**
 * Класс {@code RemoveLastCommand} переопределяет метод {@code execute()} для удаления
 * последнего элемента из {@code Collection <? extends Shorty> col}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class RemoveLastCommand extends AbstractCommand {

    private Connection currentConnection;

    public RemoveLastCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Удалить последний элемент в коллекции.");
    }

    @Override
    public synchronized String execute() {
        CollectionManager manager = CollectionManager.getInstance();
        List<Shorty> collection = manager.getCitizens();
        if (collection.size() == 0) return "Коллекция пуста.";
        else {
            ListIterator<Shorty> listIterator = collection.listIterator();
            while (listIterator.hasPrevious()) {
                Shorty competitor = listIterator.previous();
                if (competitor.getMasterID() == currentConnection.getMasterID()) {
                    collection.remove(competitor);
                    return "Последний созданный вами элемент успешно удалён.";
                }
            } return "В коллекции нет созданных вами элементов.";
        }
    }
}