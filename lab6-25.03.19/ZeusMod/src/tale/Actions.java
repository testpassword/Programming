package tale;

/**
 * {@code LotActions} Действия, связанные с выводом информации о лоте (деньгах, акциях).
 * @author Артемий Кульбако
 * @version 2.0
 * @since 20.12.18
 */
interface LotActions {
    void showBalance(AbstractBalance bal);
}

/**
 * {@code StocksActions} Интерфейс, предоставляющий методы для выполнения действий с акциями.
 */
interface StocksActions extends LotActions {
    void buyStocks(int n) throws LocationException;
    void soldStocks(int n) throws LocationException;
}

/**
 * {@code MoneyActions} Интерфейс, предоставляющий методы для выполнения действий деньгами.
 */
 interface MoneyActions extends LotActions {
    void putMoneyToBank(double money) throws LocationException;
    void getMoneyFromBank(double money) throws LocationException;
}