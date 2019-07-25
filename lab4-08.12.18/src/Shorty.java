import java.util.Objects;

public class Shorty implements StocksActions, MoneyActions, Status, LotActions {
    private String name;
    private WalletBalance cash;
    private BankBalance account;
    private Town.Place space;
    private int reputation = 0;


    Shorty(Town.Place space, WalletBalance cash, BankBalance account, String name) {
        this.name = name;
        this.cash = cash;
        this.account = account;
        this.space = space;
    }

    Shorty(Town.Place space, WalletBalance cash, BankBalance account) {
        this.name = "неизвестный коротышка";
        this.cash = cash;
        this.account = account;
        this.space = space;
    }

    public Town.Place getPlace() {
        return space;
    }

    public double getMoney(){
        return cash.getMoney();
    }

    public void move(Town.Place space) {
        this.space = space;
        System.out.println("> " + name + " перешёл в локацию " + space.toString());
    }

    @Override
    public void showBalance() {
        System.out.println("> Баланс коротышки - " + name);
        System.out.println("    Корманный баланс: Фертинги = " + cash.getMoney() + " | Акции = " + cash.getStocks());
        System.out.println("    Банковский баланс: Фертинги = " + account.getMoney());
    }

    @Override
    public void buyStocks(int n) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(TypeOfLocation.GIGANTIC_PLANT_SOCIETY)) throw new LocationException(space);
                int x = (int) (cash.getMoney() / Town.GiganticPlantSociety.getStockPriceForBuying());
                if ((n <= x) && (n <= Town.GiganticPlantSociety.storage.getStocks())) {
                    cash.setStocks(n);
                    Town.GiganticPlantSociety.storage.setMoney(Town.GiganticPlantSociety.storage.getMoney() + n * Town.GiganticPlantSociety.getStockPriceForBuying());
                    Town.GiganticPlantSociety.storage.setStocks(Town.GiganticPlantSociety.storage.getStocks() - n);
                    System.out.println("> " + name + " приобрёл " + n + " акций");
                    cash.setMoney(cash.getMoney() - (n * Town.GiganticPlantSociety.getStockPriceForBuying()));
                } else lotProhibition();
        } else reputationProhibition();
    }

    @Override
    public void soldStocks(int n) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(TypeOfLocation.GIGANTIC_PLANT_SOCIETY)) throw new LocationException(space);
                if ((n <= cash.getStocks()) & (n * Town.GiganticPlantSociety.getStockPriceForSold() <= Town.GiganticPlantSociety.storage.getMoney())) {
                    int x = 0 - n;
                    cash.setMoney(x * Town.GiganticPlantSociety.getStockPriceForSold());
                    Town.GiganticPlantSociety.storage.setStocks(Town.GiganticPlantSociety.storage.getStocks() + n);
                    Town.GiganticPlantSociety.storage.setMoney(Town.GiganticPlantSociety.storage.getMoney() - x * Town.GiganticPlantSociety.getStockPriceForSold());
                    System.out.println("> " + name + " продал " + n + " акций");
                    cash.setStocks(cash.getStocks() - x);
                } else lotProhibition();
        } else reputationProhibition();
    }

    @Override
    public void putMoneyToBank(double money) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(TypeOfLocation.BANK)) throw new LocationException(space);
                if (money <= cash.getMoney()) {
                    account.setMoney(money * Town.Bank.getRate());
                    cash.setMoney(cash.getMoney() - money);
                    System.out.println("> " + name + " положил " + money + " в банк");
                } else lotProhibition();
        } else reputationProhibition();
    }

    @Override
    public void getMoneyFromBank(double money) {
        if (reputation >= 0) {
            if (!space.getTypeOfPlace().equals(TypeOfLocation.BANK)) throw new LocationException(space);
                if (money <= account.getMoney()) {
                    cash.setMoney(cash.getMoney() + money);
                    account.setMoney(account.getMoney() - money);
                } else lotProhibition();
        } else reputationProhibition();
    }

    public void robShorty(Shorty man)  {
        int chance = (int) (1 + Math.random() * 10);
        if (!(space.equals(man.space))) throw new LocationException(space);
        if (chance > 3) {
            cash.setMoney(cash.getMoney() + man.cash.getMoney());
            man.cash.setMoney(0);
            System.out.println("> " + name + " ограбил коротышку - " + man.name);
            reputation -= 50;
        } else System.out.println("> Ограбление не удалось");
    }

    public void shakeHand(Shorty man) {
        if (!space.equals(man.space)) throw new LocationException(space);
            reputation++;
            man.reputation++;
            System.out.println("> " + name + " пожал руку " + man.name + ".");
            System.out.println("    репутация коротышки " + name + " = " + reputation);
            System.out.println("    репутация коротышки " + man.name + " = " + man.reputation);
    }

    @Override
    public String toString() {
        return "Shorty{" +
                "name='" + name + '\'' +
                ", cash=" + cash +
                ", account=" + account +
                ", space=" + space +
                ", reputation=" + reputation +
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
                space == shorty.space;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cash, account, space, reputation);
    }

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
    }