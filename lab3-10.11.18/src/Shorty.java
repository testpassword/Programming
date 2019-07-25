import java.util.Objects;

public class Shorty implements StocksActions, MoneyActions, StatusActions{
    private String name;
    private PocketBalance cash;
    private BankBalance account;
    private TypeOfLocation place;


    @Override
    public String toString() {
        return "Shorty{" +
                "name='" + name + '\'' +
                ", cash=" + cash +
                ", account=" + account +
                ", place=" + place +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shorty)) return false;
        Shorty shorty = (Shorty) o;
        return Objects.equals(name, shorty.name) &&
                Objects.equals(cash, shorty.cash) &&
                Objects.equals(account, shorty.account) &&
                place == shorty.place;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cash, account, place);
    }

    Shorty(TypeOfLocation place, PocketBalance cash, BankBalance account, String name) {
        this.name = name;
        this.cash = cash;
        this.account = account;
        this.place = place;
    }

    Shorty(TypeOfLocation place, PocketBalance cash, BankBalance account) {
        this.name = "неизвестный коротышка";
        this.cash = cash;
        this.account = account;
        this.place = place;
    }

/*
        protected typeOfLocation getLocation(){
            return place;
        }
*/

    @Override
    public void showBalance() {
        System.out.println("> Баланс коротышки - " + name);
        if (cash != null) System.out.println("    Корманный баланс: Деньги = " + cash.getMoney() + " | Акции = " + cash.getStocks());
        if (account != null) System.out.println("    Банковский баланс: Деньги = " + account.getMoney());
    }

    @Override
    public void buyStocks(int n, GiganticPlantSociety officeID) {
        if (place.equals(TypeOfLocation.GIGANTIC_PLANT_SOCIETY)) {
            int x = (int) (cash.getMoney() / GiganticPlantSociety.getStockPriceForBuying());
            if ((n <= x) && (n <= officeID.storage.getStocks())) {
                cash.setStocks(n);
                officeID.storage.setMoney(officeID.storage.getMoney() + n * GiganticPlantSociety.getStockPriceForBuying());
                officeID.storage.setStocks(officeID.storage.getStocks() - n);
                System.out.println("> " + name + " приобрёл " + n + " акций");
                cash.setMoney(cash.getMoney() - (n * GiganticPlantSociety.getStockPriceForBuying()));
            } else System.out.println("> " + name + " не имеет достаточно денег для приобретения такого количества акций, или акции кончились");
        } else displayProhibition();
    }

    @Override
    public void soldStocks(int n, GiganticPlantSociety officeID) {
        if (place.equals(TypeOfLocation.GIGANTIC_PLANT_SOCIETY)) {
            if ((n <= cash.getStocks()) & (n * GiganticPlantSociety.getStockPriceForSold() <= officeID.storage.getMoney())) {
                cash.setMoney(n * GiganticPlantSociety.getStockPriceForSold());
                officeID.storage.setStocks(officeID.storage.getStocks() + n);
                officeID.storage.setMoney(officeID.storage.getMoney() - n * GiganticPlantSociety.getStockPriceForSold());
                System.out.println("> " + name + " продал " + n + " акций");
                cash.setStocks(cash.getStocks() - n);
            }
            else System.out.println("> " + name + " не имеет столько акций, или отделение ОГР не имеет денег");
        } else displayProhibition();
    }

    @Override
    public void putMoneyToBank(double money){
            if (place.equals(TypeOfLocation.BANK)){
                if (money <= cash.getMoney()){
                    account.setMoney(account.getMoney() + money);
                    cash.setMoney(cash.getMoney() - money);
                    System.out.println("> " + name + " положил " + money + " в банк");
                }
                else System.out.println("> " + name + " не имеет столько наличных или Общество не может купить его акции");
            }
            else displayProhibition();
    }

    public void putMoneyToBank(){
        if (place.equals(TypeOfLocation.BANK)){
                account.setMoney(account.getMoney() + cash.getMoney());
                System.out.println("> " + name + " положил " + cash.getMoney() + " в банк");
                cash.setMoney(0);
            }
            else System.out.println("> " + name + " не имеет столько наличных или Общество не может купить его акции");
    }

    @Override
    public void getMoneyFromBank(double money){
            if (place.equals(TypeOfLocation.BANK)){
                if (money <= account.getMoney()){
                    cash.setMoney(cash.getMoney() + money);
                    account.setMoney(account.getMoney() - money);
                }
                else System.out.println("> " + name + " не имеет столько денег на банковском счету");
            }
            else displayProhibition();
    }
    @Override
    public void robOtherShortly(Shorty man) {
        if (place.equals(TypeOfLocation.STREET)) {
            cash.setMoney(cash.getMoney() + man.cash.getMoney());
            man.cash.setMoney(0);
            System.out.println(name + " ограбил " + name);
        }
        else displayProhibition();
    }
}