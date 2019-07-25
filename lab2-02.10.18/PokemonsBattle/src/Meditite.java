import ru.ifmo.se.pokemon.*;

public class Meditite extends Pokemon {
    public Meditite(String name, int level) {
        super(name, level);
        setStats(30, 40, 55, 40, 55, 60);
        setType(Type.FIGHTING, Type.PSYCHIC);
        setMove(new WorkUp(), new Confide(), new RockSlide());
    }
}