package commands;

import server.CollectionManager;
import tale.Shorty;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс {@code RemoveLastCommand} переопределяет метод {@code execute ()} для удаления
 * последнего элемента из {@code Collection <? extends Shorty> col}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class RemoveLastCommand extends AbstractCommand {

    public RemoveLastCommand(CollectionManager manager) {
        super(manager);
        setDescription("Удалить последний элемент в коллекции.");
    }

    @Override
    public synchronized String execute() {
        List<Shorty> collection = getManager().getCitizens();
        try {
            collection.remove(collection.size() - 1);
            getManager().save();
            return "Последний элемент в коллекции удалён.";
        }
        catch (NoSuchElementException ex) {
            return "Вы не можете удалить элемент, так как коллекция пуста.";
        }
    }
}