package tale;

import java.util.Date;
import java.util.Objects;

/**
 * Объекты класса {@code Shorty} имитируют коротышку - жителя цветочного города.
 * @author Артемий Кульбако
 * @version 2.1
 * @since 01.05.19
 */
public class Shorty implements StocksActions, MoneyActions, Comparable<Shorty> {

    private static byte anonCounter;
    private String name;
    private WalletBalance cash;
    private BankBalance account;
    private Town.Place space;
    private int reputation;
    private Date bornDate;

    {reputation = 0;}

    static {anonCounter = 1;}

    public Shorty(String name, WalletBalance cash, BankBalance account, Town.Place space, Date bornDate) {
        this.name = name;
        this.cash = cash;
        this.account = account;
        this.space = space;
        this.bornDate = bornDate;
    }

    public Shorty(WalletBalance cash, BankBalance account, Town.Place space, Date bornDate) {
        this.name = "noname#" + anonCounter;
        this.cash = cash;
        this.account = account;
        this.space = space;
        this.bornDate = bornDate;
        anonCounter++;
    }

    public Town.Place getPlace() {
        return space;
    }

    public WalletBalance getWallet(){
        return cash;
    }

    public BankBalance getAccount(){
        return account;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    /**
     * Перемещает {@link Shorty} из одного места в другое. Доступно из всех типов мест.
     * @param space место, куда вы хотите переместиться.
     */
    public void setPlace(Town.Place space) {
        this.space = space;
        System.out.println("> " + name + " перешёл в из " + this.space.toString() + " в локацию " + space.toString());
    }

    /**
     * Отображает текущие значения {@link WalletBalance} и {@link BankBalance} у вызвающего {@link Shorty}.
     * Доступно из всех типов мест.
     */
    @Override
    public void showBalance(AbstractBalance bal) {
        System.out.println("> Баланс коротышки: " + name);
        if (bal instanceof WalletBalance) System.out.println("  Фертинги - " +
                bal.getMoney() + " Акции - " + ((WalletBalance) bal).getStocks());
        if (bal instanceof BankBalance) System.out.println("    Фертинги - " + bal.getMoney());
    }

    /**
     * Увеличивает количество акций коротышки, если у него достаточно денег для покупки.
     * Доступно в {@code TypeOfLocation.GIGANTIC_PLANT_SOCIETY}.
     * @param n Количество приобретаемых акций.
     */
    @Override
    public void buyStocks(int n) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(Town.TypeOfLocation.GIGANTIC_PLANT_SOCIETY)) throw new LocationException(space);
                int x = (int) (cash.getMoney() / Town.GiganticPlantSociety.getStockPriceForBuying());
                if ((n <= x) && (n <= Town.GiganticPlantSociety.storage.getStocks())) {
                    cash.setStocks(n);
                    Town.GiganticPlantSociety.storage.setMoney(Town.GiganticPlantSociety.storage.getMoney() +
                            n * Town.GiganticPlantSociety.getStockPriceForBuying());
                    Town.GiganticPlantSociety.storage.setStocks(Town.GiganticPlantSociety.storage.getStocks() - n);
                    System.out.println("> " + name + " приобрёл " + n + " акций");
                    cash.setMoney(cash.getMoney() - (n * Town.GiganticPlantSociety.getStockPriceForBuying()));
                } else throw new LackOfLotException(n);
        } else throw new IllegalStateException("> Нельзя совершить это действие, т.к. репутация коротышки отрицательна.");
    }

    /**
     * Уменьшает акции коротышки, если у него достаточно акций для продажи.
     * Доступно в {@code TypeOfLocation.GIGANTIC_PLANT_SOCIETY}.
     * @param n Количество проданных акций.
     */
    @Override
    public void soldStocks(int n) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(Town.TypeOfLocation.GIGANTIC_PLANT_SOCIETY)) throw new LocationException(space);
                if ((n <= cash.getStocks()) & (n * Town.GiganticPlantSociety.getStockPriceForSold() <=Town.GiganticPlantSociety.storage.getMoney())) {
                    int x = 0 - n;
                    cash.setMoney(x * Town.GiganticPlantSociety.getStockPriceForSold());
                    Town.GiganticPlantSociety.storage.setStocks(Town.GiganticPlantSociety.storage.getStocks() + n);
                    Town.GiganticPlantSociety.storage.setMoney(Town.GiganticPlantSociety.storage.getMoney() -
                            x * Town.GiganticPlantSociety.getStockPriceForSold());
                    System.out.println("> " + name + " продал " + n + " акций");
                    cash.setStocks(cash.getStocks() - x);
                } else throw new LackOfLotException(n);
        } else throw new IllegalStateException("> Нельзя совершить это действие, т.к. репутация коротышки отрицательна.");
    }

    /**
     * Перемещает деньги из {@link WalletBalance} в {@link BankBalance} (== положить деньги в банк).
     * Эти деньги будут умножены в соответствии с коэффициентом из класса {@link Town.Bank} и их нельзя украсть.
     * Доступно в типе местоположения {@code TypeOfLocation.BANK}.
     * @param money Сумма, которую нужно внести в банк.
     */
    @Override
    public void putMoneyToBank(double money) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(Town.TypeOfLocation.BANK)) throw new LocationException(space);
                if (money <= cash.getMoney()) {
                    account.setMoney(money * Town.Bank.getRate());
                    cash.setMoney(cash.getMoney() - money);
                    System.out.println("> " + name + " положил " + money + " в банк");
                } else throw new LackOfLotException(money);
        } else throw new IllegalStateException("> Нельзя совершить это действие, т.к. репутация коротышки отрицательна.");
    }

    /**
     * Перемещает деньги из {@link BankBalance} в {@link WalletBalance} (== взять из банка).
     * Доступно в типе местоположения {@code TypeOfLocation.BANK}.
     * @param money Сумма, которую нужно взять в банке.
     */
    @Override
    public void getMoneyFromBank(double money) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(Town.TypeOfLocation.BANK)) throw new LocationException(space);
                if (money <= account.getMoney()) {
                    cash.setMoney(cash.getMoney() + money);
                    account.setMoney(account.getMoney() - money);
                } else throw new LackOfLotException(money);
        } else throw new IllegalStateException("> Нельзя совершить это действие, т.к. репутация коротышки отрицательна.");
    }

    /**
     * Объект типа {@link Shorty} получает сумму от объекта {@link WalletBalance}, принадлежащего
     * другому объекту типа {@link Shorty}. Вероятность успешного грабежа = 30%. репутация грабителя упадет на 50 очков.
     * Доступно в {@code TypeOfLocation.STREET}.
     * @param man Объект типа {@link Shorty}, который будет ограблен.
     */
    public void robShorty(Shorty man)  {
        int chance = (int) (1 + Math.random() * 10);
        if (!(space.equals(man.space))) throw new LocationException(space);
        if (chance > 3) {
            cash.setMoney(cash.getMoney() + man.cash.getMoney());
            man.cash.setMoney(0);
            System.out.println("> " + name + " ограбил коротышку - " + man.name);
            reputation -= 50;
        } else {
            System.out.println("> Ограбление не удалось");
            reputation -= 50;
        }
    }

    /**
     * Объект типа {@link Shorty} пожимает руку другому объекту типа {@link Shorty}. Репутация обоих
     * увеличится на 1 балл. Доступно из всех типов мест, при условии {@code space.equals(man.space)}.
     * @param man Объект типа {@link Shorty}, который пожмет руку.
     */
    public void shakeHand(Shorty man) {
        if (!space.equals(man.space)) throw new LocationException(space);
            reputation++;
            man.reputation++;
            System.out.println("> " + name + " пожал руку " + man.name + "");
            System.out.println("    репутация коротышки " + name + " = " + reputation);
            System.out.println("    репутация коротышки " + man.name + " = " + man.reputation);
    }

    /**
     * Объект типа {@link Shorty} сообщает другому объекту типа {@link Shorty} одну из трех случайных историй.
     * Репутация слушателя повышена на 10 баллов. Доступно из всех типов мест, при условии {@code space.equals(man.space)}.
     * @param man Объект типа {@link Shorty}, которому будет рассказана история.
     */
    void toldStory(Shorty man)  {
        if (!space.equals(man.space)) throw new LocationException(space);
        int a = (int) (1 + Math.random() * 6);
        switch (a) {
            case 1:
            case 2:
                System.out.println("> " + name + " делится своей биографией с " + man.name);
                System.out.println("    \"Я мечтал поступить куда-нибудь на завод или на фабрику и подзаработать денег, чтоб прикупить земли, так как мой клочок давал очень небольшой урожай. В конце концов мне удалось устроиться рабочим на фабрику, однако за долгие годы работы я так и не смог скопить сумму, которой хватило бы на покупку земли.\"");
                break;
            case 3:
            case 4:
                System.out.println("> " + name + " делится своей биографией с " + man.name);
                System.out.println("    \"Друг рассказал мне о вашем обществе. Решил прикупил немного акций, вдруг не прогарю.\"");
                break;
            case 5:
            case 6:
                System.out.println("> " + name + " делится своей биографией с " + man.name);
                System.out.println("    \"Я помню как акции одного нефтяного общества, вот название уже позабыл, выросли в десять раз. Тогда я акции не покупал, а сейчас куплю. Контора у вас от народа.\"");
                break;
        }
        man.reputation += 10;
    }

    @Override
    public int compareTo(Shorty p){
        return getName().compareTo(p.getName());
    }

    public String getName() {
        return name;
    }

    public int getReputation() {
        return reputation;
    }

    @Override
    public String toString() {
        return "Коротышка{" +
                "имя = " + name  +
                ", деньги в кошельке = " + cash +
                ", банковский счёт = " + account +
                ", space=" + space +
                ", репутация = " + reputation +
                ", дата = " + bornDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shorty)) return false;
        Shorty shorty = (Shorty) o;
        return reputation == shorty.reputation &&
                Objects.equals(name, shorty.name) &&
                Objects.equals(cash, shorty.cash) &&
                Objects.equals(account, shorty.account) &&
                Objects.equals(space, shorty.space);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cash, account, space, reputation);
    }
}

//TODO запрерить отрицательные значения акций и денег у коротышек