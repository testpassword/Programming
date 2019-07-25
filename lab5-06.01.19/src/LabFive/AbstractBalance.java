package LabFive;

import java.util.Objects;

/**
 * {@code Balance} Абстрактный класс, нужный для создания различных систем хранения ценных бумаг.
 * Содержит сумму денег, представленную типом double.
 */
abstract public class AbstractBalance {

    private double sum;

    public AbstractBalance(double sum){
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBalance)) return false;
        AbstractBalance balance = (AbstractBalance) o;
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