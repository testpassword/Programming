import ru.ifmo.se.pokemon.*;

public class Entei extends Pokemon {
    public Entei(String name, int level) {
        super(name, level);
        setStats(115, 115, 85, 90, 75, 100);
        setType(Type.FIRE);
        setMove(new Leer(), new Stomp(), new FlameCharge(), new CalmMind());
    }
}