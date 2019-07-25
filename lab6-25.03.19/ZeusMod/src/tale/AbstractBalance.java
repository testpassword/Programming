package tale;

/**
 * {@code Balance} Абстрактный класс, необходимый для создания различных систем хранения ценных бумаг.
 * Содержит сумму денег, представленную типом double.
 * @author Артемий Кульбако
 * @version 2.0
 * @since 20.12.18
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

    public void setMoney(double sum) {
        this.sum = sum;
    }

    public double getMoney() {
        return sum;
    }
}