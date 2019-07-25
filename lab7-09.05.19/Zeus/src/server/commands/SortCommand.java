package server.commands;

import server.managers.CollectionManager;
import tale.Shorty;
import java.util.Comparator;
import java.util.List;

/**
 * {@code SortCommand} класс переопределяет метод {@code execute()} для сортировки {@code Collection<? extends Shorty> col}
 * по одному из параметров: имени {@code Comparator.comparing(Shorty::getName)},
 * дате рождения {@code Comparator.comparing(Shorty::getBornDate)},
 * капиталу {@code Comparator.comparing(Shorty::getMoney)}.
 * @author Артемий Кульбако
 * @version 1.7
 * @since 18.05.19
 */
public class SortCommand extends AbstractCommand {

    public SortCommand() {
        setDescription("Упорядочивает коллекцию по возрастанию по одному из заданных параметров: " +
                "-n по имени, -m по капиталу, -d по дате рождения, -r по репутации.");
    }

    @Override
    public synchronized String execute(String[] arg) {
        CollectionManager manager = CollectionManager.getInstance();
            List<Shorty> collection = manager.getCitizens();
            if (collection.size() != 0) {
                switch (arg[0]) {
                    case "-n":
                        collection.sort(Comparator.comparing(Shorty::getName));
                        manager.save();
                        return "Коллекция упорядочена по имени.";
                    case "-d":
                        collection.sort(Comparator.comparing(Shorty::getBirthDate));
                        manager.save();
                        return "Коллекция упорядочена по дате рождения.";
                    case "-m":
                        collection.sort(Comparator.comparing(Shorty::getMoneyCapital));
                        manager.save();
                        return "Коллекция упорядочена по капиталу.";
                    default:
                        return "Неправильный параметр. Синтаксис 'sort -{n / d / m}'.";
                }
            } else return "Коллекция пуста. Нечего упорядочивать.";
    }
}