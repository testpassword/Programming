import ru.ifmo.se.pokemon.*;

/*
class MagicalLeaf extends SpecialMove{
    protected MagicalLeaf(){
            super(Type.GRASS, 60, 999999999);
        }
    @Override
    protected void applyOppEffects(Pokemon p){
     p.setMod(Stat.ACCURACY, 0);
     p.setMod(Stat.EVASION, 0);
    }
}

class EnergyBall extends SpecialMove{
    protected EnergyBall(){
            super(Type.GRASS, 90, 100);
        }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.1) p.setMod(Stat.SPECIAL_DEFENSE, -1);
    }
}
*/
class PlayNice extends StatusMove{
    protected PlayNice(){
            super(Type.NORMAL, 0, 0);
        }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.ATTACK, -1);
    }
    @Override
    protected String describe(){
        return "Понижает атаку цели на одну ступень";
    }
}
/*
class Bite extends PhysicalMove{
    protected Bite(){
        super(Type.DARK, 60, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 30) Effect.flinch(p);
    }
}

class Eruption extends SpecialMove{
    protected Eruption(){
        super(Type.FIRE, 150, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage){
        if (def.getHP() != def.getStat(Stat.HP))
        def.setMod(Stat.HP, (int) ((def.getHP()) / def.getStat(Stat.HP) * 150));
    }
}

class Ember extends SpecialMove{
    protected Ember(){
        super(Type.FIRE, 40, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.1) Effect.burn(p);
    }
}
*/
class Leer extends StatusMove{
    protected Leer(){
        super(Type.NORMAL, 0, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.ATTACK, -1);
    }
    @Override
    protected String describe(){
        return "Понижает защиту цели на одну ступень";
    }
}
/*
class LeafStorm extends SpecialMove{
    protected LeafStorm(){
        super(Type.GRASS, 130, 90);
    }
    @Override
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.SPECIAL_ATTACK, -2);
    }
}

class Swagger extends StatusMove{
    protected Swagger(){
        super(Type.NORMAL, 0, 85);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
    p.setMod(Stat.ATTACK, 2);
    Effect.confuse(p);
    }
}

class PlayRough extends PhysicalMove{
    protected PlayRough(){
        super(Type.FAIRY, 90, 90);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.1) Effect.flinch(p);
        p.setMod(Stat.ATTACK, -1);
    }
}

class Confusion extends SpecialMove{
    protected Confusion(){
        super(Type.PSYCHIC, 50, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.1) Effect.confuse(p);
    }
}

class FirePunch extends PhysicalMove {
    protected FirePunch() {
        super(Type.FIRE, 75, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.1) Effect.burn(p);
    }
}
*/
class Stomp extends PhysicalMove{
    protected Stomp(){
        super(Type.NORMAL, 65, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.3) Effect.flinch(p);
        p.setMod(Stat.ACCURACY, 0);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage){
        def.setMod(Stat.HP, (int) Math.round(damage) * 2);
    }
    @Override
    protected String describe(){
        return "имеет 30% вероятность заставить цель дрогнуть";
    }
}
/*
class ShadowBall extends SpecialMove{
    protected ShadowBall(){
        super(Type.GHOST, 80, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.2) p.setMod(Stat.SPECIAL_DEFENSE, -1);
    }
}
*/
class CalmMind extends StatusMove {
    protected CalmMind() {
        super(Type.PSYCHIC, 0, 0);
    }
        @Override
        protected void applySelfEffects (Pokemon p){
            p.setMod(Stat.SPECIAL_ATTACK, 1);
            p.setMod(Stat.SPECIAL_DEFENSE, 1);
        }
    @Override
    protected String describe(){
        return "Повышает свою спец. атаку и спец. защиту на одну ступень каждую";
    }
    }
/*
class ForcePalm extends PhysicalMove{
    protected ForcePalm() {
        super(Type.FIGHTING, 60, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.3) Effect.paralyze(p);
    }
}
*/
class FlameCharge extends PhysicalMove {
    protected FlameCharge() {
        super(Type.FIRE, 50, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.SPEED, 1);
    }
    @Override
    protected String describe(){
        return "имеет 100% вероятность повысить скорость использующего на одну ступень";
    }
}

class WorkUp extends StatusMove {
    protected WorkUp(){
        super(Type.NORMAL, 0, 0);
    }
    @Override
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.ATTACK, 1);
        p.setMod(Stat.SPECIAL_ATTACK, 1);
    }
    @Override
    protected String describe(){
        return "Повышает атаку и спец. атаку использующего на одну ступень каждую";
    }
}

class Confide extends StatusMove {
    protected Confide(){
        super(Type.NORMAL, 0, 0);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.SPECIAL_ATTACK, -1);
    }
    @Override
    protected String describe(){
        return "Снижает спец. атаку цели на один пункт";
    }
}

class RockSlide extends PhysicalMove {
    protected RockSlide(){
        super(Type.ROCK, 75, 90);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.3) Effect.flinch(p);
    }
    @Override
    protected String describe(){
        return "имеет 30% вероятность заставить противника дрогнуть";
    }
}

class ZenHeadbutt extends PhysicalMove{
    protected ZenHeadbutt(){
        super(Type.PSYCHIC, 80, 90);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.2) Effect.flinch(p);
    }
    @Override
    protected String describe(){
        return "имеет 20% вероятность заставить цель дрогнуть";
    }
}

class SweetScent extends StatusMove{
    protected SweetScent(){
        super(Type.NORMAL, 0, 0);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.EVASION, -1);
    }
    @Override
    protected String describe(){
        return "Понижает уклонение цели на одну ступень";
    }
}

class TropKick extends PhysicalMove{
    protected TropKick(){
        super(Type.GRASS, 70, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.ATTACK, -1);
    }
    @Override
    protected String describe(){
        return "Понижает атаку цели на одну ступень";
    }
}

class Facade extends PhysicalMove{
    protected Facade(){
        super(Type.NORMAL, 70, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage){
        Status PokCon = def.getCondition();
        if (PokCon.equals(Status.BURN) || PokCon.equals(Status.POISON) || PokCon.equals(Status.PARALYZE)) {
            def.setMod(Stat.HP, (int) Math.round(damage) * 2);
        }
    }
    @Override
    protected String describe(){
        return "Сила удваивается, если использующий обожжён, парализован или отравлен";
    }
}