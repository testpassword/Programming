package tale;

/**
 * {@code LackOfLotException} Ошибки, вызванные отсутствием ценных ресурсов, вовлеченных в операцию.
 * @author Артемий Кульбако
 * @version 2.0
 * @since 20.12.18
 */
public class LackOfLotException extends RuntimeException{

    public LackOfLotException(double sum){
        super("Не хватает фертингов для совершения операции, " + sum);
    }

    public LackOfLotException(int amount){
        super("Не хватает акций для совершения операции, " + amount);
    }
}