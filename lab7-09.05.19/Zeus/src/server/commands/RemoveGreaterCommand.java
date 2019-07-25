package server.commands;

import com.google.gson.JsonSyntaxException;
import server.Connection;
import server.managers.CollectionManager;
import tale.Shorty;
import java.util.List;

/**
 * Класс {@code RemoveGreaterCommand} переопределяет метод {@code execute()}, чтобы удалить все элементы из коллекции
 * {@code Collection <? extends Shorty> col}, которые больше чем {@link Shorty}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class RemoveGreaterCommand extends AbstractCommand {

    private Connection currentConnection;

    public RemoveGreaterCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Удаляет все элементы из коллекции, которые превыщают заданный.");
    }

    @Override
    public synchronized String execute(String[] args) {
        CollectionManager manager = CollectionManager.getInstance();
            List<Shorty> collection = manager.getCitizens();
            if (collection.size() != 0) {
                int beginSize = collection.size();
                try {
                    collection.removeIf(p -> (p != null
                            && p.compareTo(manager.getSerializer().fromJson(args[0], Shorty.class)) > 0
                            && p.getMasterID() == currentConnection.getMasterID()));
                    manager.save();
                    return "Удалено из коллекции " + (beginSize - collection.size()) + " элементов.";
                } catch (JsonSyntaxException ex) {
                    return "Синтаксическая ошибка JSON. " + ex.getMessage() + "Не удалось удалить элемент.";
                } catch (NullPointerException e) {
                    return "null значения запрещены.";
                }
            } else return "Элементу не с чем сравнивать. Коллекция пуста.";
    }
}