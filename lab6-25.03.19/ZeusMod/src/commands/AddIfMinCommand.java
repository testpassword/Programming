package commands;

import com.google.gson.JsonSyntaxException;
import server.CollectionManager;
import tale.Shorty;

import java.util.Collections;
import java.util.List;

/**
 * Класс {@code AddIfMin} переопределяет метод {@code execute ()}, чтобы добавить {@link Shorty} в коллекцию,
 * если он меньше {@code Collections.min(Collection <? extends Shorty> col)}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class AddIfMinCommand extends AbstractCommand {

    public AddIfMinCommand(CollectionManager manager) {
        super(manager);
        setDescription("Добавьте новый элемент в коллекцию, если его значение меньше, чем наименьший элемент в этой коллекции.");
    }

    @Override
    public synchronized String execute(String arg) {
            List<Shorty> collection = getManager().getCitizens();
            if (collection.size() != 0) {
                try {
                    Shorty mainCompetitor = getManager().getSerializer().fromJson(arg, Shorty.class);
                    if (Collections.min(collection).compareTo(mainCompetitor) > 0) {
                        collection.add(mainCompetitor);
                        getManager().save();
                        return "Элемент успешно добавлен.";
                    } else return "Не удалось добавить элемент.";
                } catch (JsonSyntaxException e) {
                    return "Синтаксическая ошибка JSON. Не удалось добавить элемент.";
                }
            }
            else return "Элемент не с чем сравнивать. Коллекция пуста.";
    }
}