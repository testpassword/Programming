package tale;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Объекты класса {@code Shorty} имитируют коротышку - жителя цветочного города.
 * @author Артемий Кульбако
 * @version 2.4
 * @since 21.05.19
 */
public class Shorty implements Comparable<Shorty> {

    private String name;
    private double x;
    private double y;
    private LocalDateTime birthday;
    private int masterID;

    public Shorty(String name, double x, double y, LocalDateTime birthday, int masterID) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.birthday = birthday;
        this.masterID = masterID;
    }


    @Override
    public int compareTo(Shorty p){
        return getName().compareTo(p.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    @Override
    public String toString() {
        return "Коротышка{" +
                "имя = " + name  +
                ", координаты: x=" + x  + " y=" + y +
                ", дата = " + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shorty)) return false;
        Shorty shorty = (Shorty) o;
        return masterID == shorty.masterID &&
                Objects.equals(name, shorty.name) &&
                x == shorty.x &&
                y == shorty.y &&
                Objects.equals(birthday, shorty.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, birthday, masterID);
    }
}

//TODO запрерить отрицательные значения акций и денег у коротышек