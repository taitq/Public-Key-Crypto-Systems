package ECC;

import java.math.BigInteger;
import java.util.Objects;

public class Point {
    private BigInteger x;
    private BigInteger y;
    // type = 0 : Point is normal
    // type = 1: Point is infinitive
    // type = -1 : Invalid
    private Integer type;
    public final static Point INFINITY = new Point(0, 0, 1);
    public final static Point INVALID = new Point(0, 0, -1);

    public Point(BigInteger x, BigInteger y, Integer type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Point(Integer x, Integer y, Integer type) {
        this(BigInteger.valueOf(x), BigInteger.valueOf(y), type);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x.equals(point.x) && y.equals(point.y) && type.equals(point.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, type);
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Boolean isValid()
    {
        return this.type >= 0;
    }

    public Boolean isInfinity()
    {
        return this.type == 1;
    }
}
