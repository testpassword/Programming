import ru.ifmo.se.pokemon.*;

public class Battleground {

    public static void main(String[] args) {
        Battle field = new Battle();
        field.addAlly(new Entei("Pupa", 2));
        field.addAlly(new Bounsweet("Lupa", 1));
        field.addAlly(new Medicham("Vupsen", 3));
        field.addFoe(new Meditite("Pupsen", 2));
        field.addFoe(new Steenee("Mayweather", 3));
        field.addFoe(new Tsareena("Pacquiao", 2));
        field.go();
    }
}