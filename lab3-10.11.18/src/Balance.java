abstract class Balance {
    private double sum;
    Balance(double sum){
        this.sum = sum;
    }
    public void setMoney(double sum){
        this.sum = sum;
    }
    public double getMoney() {
        return sum;
    }
}