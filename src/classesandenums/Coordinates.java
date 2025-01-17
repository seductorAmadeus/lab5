package classesandenums;

public class Coordinates {
    private double x;
    private long y;

    public Coordinates(double x, long y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public String toString() {
        return "X:" + x + " Y:" + y;
    }

    public int hashCode() {
        return (int) y + (int) x;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates) { // instanceof нужен, чтобы проверить, был ли объект, на который ссылается переменная obj, создан на основе класса Coordinates
            Coordinates coordinatesObj = (Coordinates) obj;
            return (x == coordinatesObj.getX()) && (y == coordinatesObj.getY());
        }
        return false;
    }

}
