public class LocationException extends RuntimeException{

    public LocationException(){
    }

    public LocationException(Town.Place space){
        super("Недопустимая локация, " + space);
    }
}