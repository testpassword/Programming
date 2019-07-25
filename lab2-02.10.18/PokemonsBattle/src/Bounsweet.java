import ru.ifmo.se.pokemon.*;

public class Bounsweet extends Pokemon {
    public Bounsweet(String name, int level) {
        super(name, level);
        setStats(42, 30, 38, 30, 38, 32);
        setType(Type.GRASS);
        setMove(new SweetScent(), new Facade());
    }
}