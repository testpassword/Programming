package LabFive;

/**
 * {@code LocationException} Ошибки, появляющиеся, если объект типа {@code Shortly} пытается совершить метод,
 * находясь в неподходящей локации.
 */
public class LocationException extends RuntimeException{

    public LocationException(Town.Place space){
        super("Недопустимая локация, " + space);
    }
}