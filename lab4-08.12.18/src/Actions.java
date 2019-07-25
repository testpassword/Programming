interface LotActions {
    void showBalance();
    default void lotProhibition() {System.out.println("> не имеет столь фертингов или акций");}
}

interface StocksActions extends LotActions {
    void buyStocks(int n) throws LocationException;
    void soldStocks(int n) throws LocationException;
}

 interface MoneyActions extends LotActions {
    void putMoneyToBank(double money) throws LocationException;
    void getMoneyFromBank(double money) throws LocationException;
}