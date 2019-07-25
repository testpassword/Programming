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

    public ShowCommand() {
        setDescription("Выводит все элементы коллекции.");
    }

    @Override
    public synchronized String execute() {
        CollectionManager manager = CollectionManager.getInstance();
        List<Shorty> collection = manager.getCitizens();
        if (collection.size() != 0) return manager.getSerializer().toJson(collection);
        else return "Коллекция пуста.";
    }
}