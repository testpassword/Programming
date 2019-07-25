package server.commands;

import com.google.gson.JsonSyntaxException;
import server.managers.CollectionManager;
import server.Connection;
import tale.Shorty;
import java.util.List;

/**
* Класс {@code RemoveCommand} переопределяет метод {@code execute()} для удаления {@link Shorty} из
 * {@code Collection <? extends Shorty> col}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class RemoveCommand extends AbstractCommand {

    private Connection currentConnection;

    public RemoveCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Удаляет элемент из коллекции по его значению.");
    }

    @Override
    public synchronized String execute(String[] args) {
        CollectionManager manager = CollectionManager.getInstance();
            List<Shorty> collection = manager.getCitizens();
            if (collection.size() != 0) {
                try {
                    Shorty removable = manager.getSerializer().fromJson(args[0], Shorty.class);
                    if (removable.getMasterID() == currentConnection.getMasterID()) {
                        if (collection.remove(removable)) {
                            manager.save();
                            return "Элемент успешно удалён.";
                        } else return "Такого элемента нет в коллекции.";
                    } else return "У вас не прав на удаление этого объекта.";
                } catch (JsonSyntaxException ex) {
                    return "Синтаксическая ошибка JSON. " + ex.getMessage() + "Не удалось удалить элемент.";
                } catch (NullPointerException e) {
                    return "null значения запрещены.";
                }
            }
            else return "Элемент не с чем сравнивать. Коллекция пуста.";
    }
}