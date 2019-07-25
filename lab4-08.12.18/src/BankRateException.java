public class BankRateException extends Exception{

    public BankRateException(){
    }

    public BankRateException(double n){
        super("Процентная ставка не может быть отрицательной или нулевой, " + n);
    }
}