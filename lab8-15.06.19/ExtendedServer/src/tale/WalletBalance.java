package tale;

import java.util.Objects;

/*** Объект класса {@code WalletBalance} является «кошельком» - суммой, которая хранится непосредственно в
 * владелец, и над которым вы можете соврешать действия. Содержит количество фертингов (double) и акций (int).
 * @author Артемий Кульбако
 * @version 2.0
 * @since 20.12.18
 */
public class WalletBalance {

    private double sum;
    private int amount;

    public WalletBalance(double sum, int amount) {
        this.sum = sum;
        this.amount = amount;
    }

    public void setMoney(double sum) {
        this.sum = sum;
    }

    public double getMoney() {
        return sum;
    }

    public void setStocks(int amount){
        this.amount = amount;
    }

    public int getStocks() {
        return amount;
    }

    @Override
    public String toString() {
        return "WalletBalance{" +
                "sum=" + sum +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WalletBalance)) return false;
        WalletBalance that = (WalletBalance) o;
        return Double.compare(that.sum, sum) == 0 &&
                amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, amount);
    }
}