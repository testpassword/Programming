package commands;

import com.google.gson.JsonSyntaxException;
import server.CollectionManager;
import tale.Shorty;

/**
 * Класс {@code AddCommand} переопределяет метод {@code execute ()} для добавления {@link Shorty} в коллекцию.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class AddCommand extends AbstractCommand {

    public AddCommand(CollectionManager manager) {
        super(manager);
        setDescription("Добавить новый элемент в коллекцию.");
    }

    @Override
    public synchronized String execute(String arg) {
            try {
                getManager().getCitizens().add(getManager().getSerializer().fromJson(arg, Shorty.class));
                getManager().save();
                return "Элемент успешно добавлен.";
            } catch (JsonSyntaxException ex) {
                return "Синтаксическая ошибка JSON. Не удалось добавить элемент.";
            }
    }
}