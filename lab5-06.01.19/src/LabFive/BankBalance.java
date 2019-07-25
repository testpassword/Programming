package LabFive;

/**
 * {@code BankBalance} Объекты класса инкапсулируют сумму денег (double) на банковском счету владельца.
 * Наследуется от абстрактного класса {@code Balance}.
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