public class PocketBalance extends Balance{
    private int amount;
    PocketBalance(double sum, int amount){
        super(sum);
        this.amount = amount;
    }
    public void setStocks(int amount){
        this.amount = amount;
    }
    public int getStocks() {
        return amount;
    }
}