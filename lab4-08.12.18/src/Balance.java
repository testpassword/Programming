import java.util.Objects;

abstract public class Balance {
    private double sum;
    Balance(double sum){
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Balance)) return false;
        Balance balance = (Balance) o;
        return Double.compare(balance.sum, sum) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum);
    }

    @Override
    public String toString() {
        return "Balance{" +
                "sum=" + sum +
                '}';
    }

    public void setMoney(double sum) {
        this.sum = sum;
    }
    public double getMoney() {
        return sum;
    }
}