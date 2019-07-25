package server.commands;

import com.google.gson.JsonSyntaxException;
import server.managers.CollectionManager;
import server.Connection;
import tale.Shorty;
import java.util.Collections;
import java.util.List;

/**
 * Класс {@code AddIfMin} переопределяет метод {@code execute()}, чтобы добавить {@link Shorty} в коллекцию,
 * если он меньше {@code Collections.min(Collection <? extends Shorty> col)}.
 * @author Артемий Кульбако
 * @version 1.5
 * @since 21.05.19
 */
public class AddIfMinCommand extends AbstractCommand {

    private Connection currentConnection;

    public AddIfMinCommand(Connection currentConnection) {
        this.currentConnection = currentConnection;
        setDescription("Добавьте новый элемент в коллекцию, если его значение меньше, чем наименьший элемент в этой коллекции.");
    }

    @Override
    public synchronized String execute(String[] args) {
        CollectionManager manager = CollectionManager.getInstance();
            List<Shorty> collection = manager.getCitizens();
            if (collection.size() != 0) {
                try {
                    Shorty mainCompetitor = manager.getSerializer().fromJson(args[0], Shorty.class);
                    mainCompetitor.setMasterID(currentConnection.getMasterID());
                    if (Collections.min(collection).compareTo(mainCompetitor) > 0) {
                        collection.add(mainCompetitor);
                        manager.save();
                        return "Элемент успешно добавлен.";
                    } else return "Не удалось добавить элемент.";
                } catch (JsonSyntaxException e) {
                    return "Синтаксическая ошибка JSON. " + e.getMessage() + "Не удалось добавить элемент.";
                } catch (NullPointerException e) {
                    return "null значения запрещены.";
                }
            }
            else return "Элемент не с чем сравнивать. Коллекция пуста.";
    }
}