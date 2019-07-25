package LabFive;

/**
 * {@code LotActions} Действия связанные с выводом информации о лоте (деньги, акции) владельца.
 */
interface LotActions {
    void showBalance(AbstractBalance bal);
}

/**
 * {@code StocksActions} Интерефейс, предоставляющий методы для совершения действия с акциями владельца.
 */
interface StocksActions extends LotActions {
    void buyStocks(int n) throws LocationException;
    void soldStocks(int n) throws LocationException;
}

/**
 * {@code MoneyActions} Интерефейс, предоставляющий методы для совершения действия с фертингами владельца.
 */
 interface MoneyActions extends LotActions {
    void putMoneyToBank(double money) throws LocationException;
    void getMoneyFromBank(double money) throws LocationException;
}