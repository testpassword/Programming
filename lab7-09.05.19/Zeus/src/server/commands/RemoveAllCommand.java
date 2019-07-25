package server.commands;

import com.google.gson.JsonSyntaxException;
import server.managers.CollectionManager;
import server.Connection;
import tale.Shorty;
import java.util.List;

/**
* Класс {@code RemoveAllCommand} переопределяет метод {@code execute()} для удаления всех элементов из
 * {@code Collection <? extends Shorty> col}, которые меньше чем {@link Shorty}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class RemoveAllCommand extends AbstractCommand {

    private Connection currentConnection;

    public RemoveAllCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Удалить из коллекции все предметы, эквивалентные данному.");
    }

    @Override
    public synchronized String execute(String[] args) {
        CollectionManager manager = CollectionManager.getInstance();
            List<Shorty> collection = manager.getCitizens();
            if (collection.size() != 0) {
                int beginSize = collection.size();
                try {
                    collection.removeIf(p -> p.equals(manager.getSerializer().fromJson(args[0], Shorty.class))
                            && p.getMasterID() == currentConnection.getMasterID());
                    manager.save();
                    return "Удалено из коллекции " + (beginSize - collection.size()) + " элементов.";
                } catch (JsonSyntaxException ex) {
                    return "Синтаксическая ошибка JSON. " + ex.getMessage() + "Не удалось удалить элемент.";
                } catch (NullPointerException e) {
                    return "null значения запрещены.";
                }
            }
            else return "Элемент не с чем сравнивать. Коллекция пуста.";
    }
}