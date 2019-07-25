package LabFive;

/**
 * {@code LackOfLotException} Ошибки, появляющиеся в следствие нехватки ценных ресурсов, учавствующих в операции.
 */
public class LackOfLotException extends RuntimeException{

    public LackOfLotException(double sum){
        super("Не хватает фертингов для совершения операции, " + sum);
    }

    public LackOfLotException(int amount){
        super("Не хватает акций для совершения операции, " + amount);
    }
}