import ru.ifmo.se.pokemon.*;

public class Medicham extends Meditite {
    public Medicham(String name, int level) {
        super(name, level);
        setStats(60, 60, 75, 60, 75, 80);
        setType(Type.FIGHTING, Type.PSYCHIC);
        setMove(new WorkUp(), new Confide(), new RockSlide(), new ZenHeadbutt());
    }
}