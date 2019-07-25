import java.util.Objects;

public class WalletBalance extends Balance{
    private int amount;
    WalletBalance(double sum, int amount){
        super(sum);
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "WalletBalance{" +
                "amount=" + amount +
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