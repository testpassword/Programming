package LabFive;

import java.util.Objects;

/**
 * Объекты класса {@code Shortly} имитируют коротышку - жителя цветочного города.
 */
public class Shorty implements StocksActions, MoneyActions, Comparable<Shorty> {

    private static byte anonCounter;
    private String name;
    private WalletBalance cash;
    private BankBalance account;
    transient private Town.Place space;
    private int reputation;

    {reputation = 0;}

    static {anonCounter = 1;}

    public Shorty(String name, WalletBalance cash, BankBalance account, Town.Place space) {
        this.name = name;
        this.cash = cash;
        this.account = account;
        this.space = space;
    }

    public Shorty(WalletBalance cash, BankBalance account, Town.Place space) {
        this.name = "noname#" + anonCounter;
        this.cash = cash;
        this.account = account;
        this.space = space;
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

    /**
     * Перемещает коротышку из одной локации в другую, с выводом иноформации об этих локациях.
     * @param space Локация, в которую необходимо переместиться.
     * Доступен из всех видов локаций.
     */
    public void setPlace(Town.Place space) {
        this.space = space;
        System.out.println("> " + name + " перешёл в из " + this.space.toString() + " в локацию " + space.toString());
    }

    /**
     * Отображает текущие значения {@code WalletBalance} и {@code BankBalance} коротышки.
     * Доступен из всех видов локаций.
     */

    @Override
    public void showBalance(AbstractBalance bal) {
        System.out.println("> Баланс коротышки: " + name);
        if (bal instanceof WalletBalance) System.out.println("  Фертинги - " + bal.getMoney() + " Акции - " + ((WalletBalance) bal).getStocks());
        if (bal instanceof BankBalance) System.out.println("    Фертинги - " + bal.getMoney());
    }

    /**
     * Увеличивает количество акции коротышки, если у него достаточно денег для покупки.
     * Доступен в локация типа TypeOfLocation.GIGANTIC_PLANT_SOCIETY.
     * @param n Количество покупаемых акций.
     */
    @Override
    public void buyStocks(int n) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(Town.TypeOfLocation.GIGANTIC_PLANT_SOCIETY)) throw new LocationException(space);
                int x = (int) (cash.getMoney() / Town.GiganticPlantSociety.getStockPriceForBuying());
                if ((n <= x) && (n <= Town.GiganticPlantSociety.storage.getStocks())) {
                    cash.setStocks(n);
                    Town.GiganticPlantSociety.storage.setMoney(Town.GiganticPlantSociety.storage.getMoney() + n * Town.GiganticPlantSociety.getStockPriceForBuying());
                    Town.GiganticPlantSociety.storage.setStocks(Town.GiganticPlantSociety.storage.getStocks() - n);
                    System.out.println("> " + name + " приобрёл " + n + " акций");
                    cash.setMoney(cash.getMoney() - (n * Town.GiganticPlantSociety.getStockPriceForBuying()));
                } else throw new LackOfLotException(n);
        } else throw new IllegalStateException("> Нельзя совершить это действие, т.к. репутация коротышки отрицательна.");
    }

    /**
     * Уменьшает акции коротышки, если у него достаточно акций для продажи.
     * Доступен в локация типа TypeOfLocation.GIGANTIC_PLANT_SOCIETY.
     * @param n Количество продаваемых акций.
     */
    @Override
    public void soldStocks(int n) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(Town.TypeOfLocation.GIGANTIC_PLANT_SOCIETY)) throw new LocationException(space);
                if ((n <= cash.getStocks()) & (n * Town.GiganticPlantSociety.getStockPriceForSold() <= Town.GiganticPlantSociety.storage.getMoney())) {
                    int x = 0 - n;
                    cash.setMoney(x * Town.GiganticPlantSociety.getStockPriceForSold());
                    Town.GiganticPlantSociety.storage.setStocks(Town.GiganticPlantSociety.storage.getStocks() + n);
                    Town.GiganticPlantSociety.storage.setMoney(Town.GiganticPlantSociety.storage.getMoney() - x * Town.GiganticPlantSociety.getStockPriceForSold());
                    System.out.println("> " + name + " продал " + n + " акций");
                    cash.setStocks(cash.getStocks() - x);
                } else throw new LackOfLotException(n);
        } else throw new IllegalStateException("> Нельзя совершить это действие, т.к. репутация коротышки отрицательна.");
    }

    /**
     * Перемещает деньги коротышки из {@code WalletBalance} в {@code BankBalance} (положить в банк).
     * Эти деньги будут приумножены согласно коэфиценту из класса {@code Town.Bank}.
     * Доступен в локация типа TypeOfLocation.BANK.
     * @param money Сумма, которую необходимо положить в банк.
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
     * Перемещает деньги коротышки из {@code BankBalance} в {@code WalletBalance} (взять из банка).
     * Доступен в локация типа TypeOfLocation.BANK.
     * @param money Сумма, которую необходимо взять из банка.
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
     * Объект типа {@code Shortly} получает сумму из объекта {@code WalletBalance} принадлежащего
     * другому объекту типа {@code Shortly}. Вероятность успешного ограбления = 30%. Репутация
     * коротышки-грабителя снизится на 50 пунктов.
     * Доступен в локация типа TypeOfLocation.STREET.
     * @param man Объекта типа {@code Shortly}, который будет ограблен.
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
     * Объект типа {@code Shortly} жмёт руку другому объекту типа {@code Shortly}.
     * Репутация обоих увеличится на 1 пункт.
     * Доступен в локация типа TypeOfLocation.GIGANTIC_PLANT_SOCIETY, TypeOfLocation.BANK, TypeOfLocation.STREET.
     * Локации обоих коротышек должны совпадать.
     * @param man Объекта типа {@code Shortly}, которому пожмут руку.
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
     * Объект типа {@code Shortly} расскаживает другому объекту типа {@code Shortly} одну из
     * трёх случайных историй. Репутация слушателя увеличивается на 10 пунктов.
     * Доступен в локация типа TypeOfLocation.GIGANTIC_PLANT_SOCIETY, TypeOfLocation.BANK, TypeOfLocation.STREET.
     * Локации обоих коротышек должны совпадать.
     * @param man Объекта типа {@code Shortly}, которому расскажут историю.
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
        return (int) ((cash.getMoney() + account.getMoney() + cash.getStocks() * Town.GiganticPlantSociety.getStockPriceForSold())
                        - (p.cash.getMoney() + p.account.getMoney() + p.cash.getStocks() * Town.GiganticPlantSociety.getStockPriceForSold()));
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Коротышка{" +
                "имя = " + name  +
                ", деньги в кошельке = " + cash +
                ", банковский счёт = " + account +
                ", space=" + space +
                ", репутация = " + reputation +
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

//TODO запрерить отрицательные значения акций у коротышек