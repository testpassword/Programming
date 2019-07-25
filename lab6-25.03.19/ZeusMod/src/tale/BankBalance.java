package tale;

/**
 * {@code BankBalance} Объекты класса инкапсулируют сумму денег (double) на банковском счете владельца.
 * Унаследовано от {@link AbstractBalance}.
 * @author Артемий Кульбако
 * @version 2.0
 * @since 20.12.18
 */
public class BankBalance extends AbstractBalance {

    public BankBalance(double sum){
        super(sum);
    }

    @Override
    public String toString() {
        return "BankBalance{" +
                "sum=" + super.toString() + '}';
    }
}