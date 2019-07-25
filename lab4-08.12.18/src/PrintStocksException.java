import java.lang.Math.*;

public class PrintStocksException extends RuntimeException{
    private int quantity;

    public int getAbsQuantity() {
        return quantity;
    }

    public PrintStocksException(){
    }

    public PrintStocksException(int quantity){
        super("Нельзя напечатать отрицательное число акций, " + quantity);
        this.quantity = Math.abs(quantity);
    }
}