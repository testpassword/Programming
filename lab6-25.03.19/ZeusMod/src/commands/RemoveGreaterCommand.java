package commands;

import com.google.gson.JsonSyntaxException;
import server.CollectionManager;
import tale.Shorty;
import java.util.List;

/**
 * Класс {@code RemoveGreaterCommand} переопределяет метод {@code execute ()}, чтобы удалить все элементы из коллекции
 * {@code Collection <? extends Shorty> col}, которые больше чем {@link Shorty}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class RemoveGreaterCommand extends AbstractCommand {

    public RemoveGreaterCommand(CollectionManager manager) {
        super(manager);
        setDescription("Удаляет все элементы из коллекции, которые превыщают заданный.");
    }

    @Override
    public synchronized String execute(String arg) {
            List<Shorty> collection = getManager().getCitizens();
            if (collection.size() != 0) {
                int beginSize = collection.size();
                try {
                    collection.removeIf(p -> (p != null && p.compareTo(getManager().getSerializer().fromJson(arg, Shorty.class)) > 0));
                    getManager().save();
                    return "Удалено из коллекции " + (beginSize - collection.size()) + " элементов.";
                } catch (JsonSyntaxException ex) {
                    return "Синтаксическая ошибка JSON. Не удалось удалить элемент.";
                }
            } else return "Элементу не с чем сравнивать. Коллекция пуста.";
    }
}