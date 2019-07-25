package tale;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Объекты класса {@code Shorty} имитируют коротышку - жителя цветочного города.
 * @author Артемий Кульбако
 * @version 2.4
 * @since 21.05.19
 */
public class Shorty implements LotActions, Comparable<Shorty> {

    private static byte anonCounter = 1;

    private String name;
    private WalletBalance cash;
    private Coordinates position;
    private OffsetDateTime birthDate;
    private int masterID;

    public Shorty(String name, WalletBalance cash, Coordinates position, OffsetDateTime birthDate, int masterID) {
        this.name = name;
        this.cash = cash;
        this.position = position;
        this.birthDate = birthDate;
        this.masterID = masterID;
    }

    public Shorty(WalletBalance cash, Coordinates position, OffsetDateTime birthDate, int masterID) {
        this.name = "noname#" + anonCounter;
        this.cash = cash;
        this.position = position;
        this.birthDate = birthDate;
        this.masterID = masterID;
        anonCounter++;
    }

    /**
     * Отображает текущие значения {@link WalletBalance} у вызвающего {@link Shorty}.
     */
    @Override
    public void showBalance() {
        System.out.println("> Баланс коротышки: " + name);
        System.out.println("  Фертинги - " + cash.getMoney() + " Акции - " + cash.getStocks());
    }


    @Override
    public int compareTo(Shorty p){
        return getName().compareTo(p.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WalletBalance getWallet() {
        return cash;
    }

    public void setWallet(WalletBalance cash) {
        this.cash = cash;
    }

    public Coordinates getCoordinates() {
        return position;
    }

    public void setCoordinates(Coordinates position) {
        this.position = position;
        System.out.println("> " + name + " перешёл в " + position.toString());
    }

    public OffsetDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(OffsetDateTime bornDate) {
        this.birthDate = bornDate;
    }

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public double getMoneyCapital() {
        return cash.getMoney() + cash.getStocks();
    }

    @Override
    public String toString() {
        return "Коротышка{" +
                "имя = " + name  +
                ", деньги в кошельке = " + cash +
                ", координаты =" + position +
                ", дата = " + birthDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shorty)) return false;
        Shorty shorty = (Shorty) o;
        return masterID == shorty.masterID &&
                name.equals(shorty.name) &&
                cash.getMoney() + cash.getStocks() == shorty.cash.getMoney() + shorty.cash.getStocks() &&
                position.equals(shorty.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cash, position, masterID);
    }
}

//TODO запрерить отрицательные значения акций и денег у коротышек