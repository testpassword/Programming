interface ActionsLot {
    void robOtherShortly(Shorty man);
}

interface StocksActions extends ActionsLot {
    void buyStocks(int n, GiganticPlantSociety officeID);
    void soldStocks(int n, GiganticPlantSociety officeID);
}

 interface MoneyActions extends ActionsLot {
    void putMoneyToBank(double money);
    void getMoneyFromBank(double money);
    void robOtherShortly(Shorty man);
}

interface StatusActions {
    default void displayProhibition() { System.out.println("> нельзя совершить это действие в данной локации"); }
    void showBalance();
}