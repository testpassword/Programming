package server.commands;

import server.Connection;
import server.managers.CollectionManager;
import tale.Shorty;
import java.util.List;

/**
 * Класс {@code ShowCommand} переопределяет метод {@code execute()} для отображения
 * всех элементов из {@code Collection <? extends Shorty> col}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class ShowCommand extends AbstractCommand {

    private Connection currentConnection;

    public ShowCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Выводит все элементы коллекции.");
    }

    @Override
    public synchronized String execute() {
        CollectionManager manager = CollectionManager.getInstance();
        List<Shorty> collection = manager.getCitizens();
        StringBuilder result = new StringBuilder();
        if (collection.size() != 0) {
            for (Shorty s: collection) {
                result.append(manager.getSerializer().toJson(s));
                if (s.getMasterID() == currentConnection.getMasterID()) result.append(" ВЛАДЕЛЕЦ");
                result.append("\n    ");
            }
            return result.toString();
        }
        else return "Коллекция пуста.";
    }
}