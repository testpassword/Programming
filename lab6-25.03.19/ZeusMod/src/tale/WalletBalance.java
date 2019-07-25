package tale;

import java.util.Objects;

/*** Объект класса {@code WalletBalance} является «кошельком» - суммой, которая хранится непосредственно в
 * владелец, и над которым вы можете соврешать действия. Содержит количество фертингов (double) и акций (int).
 * Унаследовано от абстрактного класса {@link AbstractBalance}.
 * @author Артемий Кульбако
 * @version 2.0
 * @since 20.12.18
 */
public class WalletBalance extends AbstractBalance {

    private int amount;

    WalletBalance(double sum, int amount){
        super(sum);
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "WalletBalance{" +
                "sum=" + super.toString() +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WalletBalance)) return false;
        if (!super.equals(o)) return false;
        WalletBalance that = (WalletBalance) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount);
    }

    public void setStocks(int amount){
        this.amount = amount;
    }

    public int getStocks() {
        return amount;
    }
}