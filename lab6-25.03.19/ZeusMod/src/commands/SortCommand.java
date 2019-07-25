package commands;

import server.CollectionManager;
import tale.Shorty;
import tale.Town;
import java.util.Comparator;
import java.util.List;

/**
 * {@code SortCommand} класс переопределяет метод {@code execute()} для сортировки {@code Collection<? extends Shorty> col}
 * по одному из параметров: имени {@code Comparator.comparing(Shorty::getName)},
 * репутации {@code Comparator.comparing(Shorty::getReputation)},
 * дате рождения {@code Comparator.comparing(Shorty::getBornDate},
 * капиталу {@code (o1, o2) -> (int) ((o1.getWallet().getMoney() + o1.getAccount().getMoney() + o1.getWallet().getStocks() *
 *                                     Town.GiganticPlantSociety.getStockPriceForSold())- (o2.getWallet().getMoney() +
 *                                     o2.getAccount().getMoney() + o2.getWallet().getStocks() *
 *                                   Town.GiganticPlantSociety.getStockPriceForSold()))}.
 * @author Артемий Кульбако
 * @version 1.4
 * @since 04.05.19
 */
public class SortCommand extends AbstractCommand {

    public SortCommand(CollectionManager manager) {
        super(manager);
        setDescription("Упорядочивает коллекцию по возрастанию по одному из заданных параметров: " +
                "-n по имени, -m по капиталу, -d по дате рождения, -r по репутации.");
    }

    @Override
    public synchronized String execute(String arg) {
            List<Shorty> collection = getManager().getCitizens();
            if (collection.size() != 0) {
                switch (arg) {
                    case "-n":
                        collection.sort(Comparator.comparing(Shorty::getName));
                        getManager().save();
                        return "Коллекция упорядочена по имени.";
                    case "-d":
                        collection.sort(Comparator.comparing(Shorty::getBornDate));
                        getManager().save();
                        return "Коллекция упорядочена по дате рождения.";
                    case "-m":
                        collection.sort((o1, o2) -> (int)
                                ((o1.getWallet().getMoney() + o1.getAccount().getMoney() + o1.getWallet().getStocks() *
                                        Town.GiganticPlantSociety.getStockPriceForSold())
                                        - (o2.getWallet().getMoney() + o2.getAccount().getMoney() + o2.getWallet().getStocks() *
                                        Town.GiganticPlantSociety.getStockPriceForSold())));
                        getManager().save();
                        return "Коллекция упорядочена по капиталу.";
                    case "-r":
                        collection.sort(Comparator.comparing(Shorty::getReputation));
                        getManager().save();
                        return "Коллекция упорядочена по репутации.";
                    default:
                        return "Неправильный параметр. Синтаксис 'sort -{n / m / d}'.";
                }
            } else return "Коллекция пуста. Нечего упорядочивать.";
    }
}