package ECC;

import java.math.BigInteger;

public class PrimeCurve {
    private BigInteger a;
    private BigInteger b;
    private FinitePrimeField p;
    // dung trong ma hoa Koblitz
    private Integer h;

    public PrimeCurve(FinitePrimeField p, BigInteger a, BigInteger b, Integer h) {
        this.p = p;
        this.a = a;
        this.b = b;
        this.h = h;
    }

    public PrimeCurve(FinitePrimeField p, Integer a, Integer b, Integer h) {
        this(p, BigInteger.valueOf(a), BigInteger.valueOf(b), h);
    }

    // cho x tim y tuong tuong ung thoa man (x,y) la 1 diem thuoc duong cong
    public Point getPoint(BigInteger x) {
        Point ret = Point.INVALID;
        x = this.p.mod(x);
        BigInteger y2 = x.pow(3).add(x.multiply(this.a)).add(this.b);
        if (p.isResidue(y2)) {
            BigInteger y = p.sqrt(y2);
            ret = new Point(x, y, 0);
        }
        return ret;
    }

    public Point getPoint(Integer x) {
        return this.getPoint(BigInteger.valueOf(x));
    }

    // tim diem nghich dao cua p tren curve
    public Point pointInverse(Point p) {
        if (p.getType() == 0) {
            p = new Point(p.getX(), this.p.opposite(p.getY()), 0);
        }
        return p;
    }

    // tinh tong 2 diem tren eliptic curve
    public Point sum(Point a, Point b) {

        // Se uno dei due punti non è valido
        if (!a.isValid() || !b.isValid()) return Point.INVALID;

        // O + B = B
        if (a.isInfinity()) return b;

        // A + O = A
        if (b.isInfinity()) return a;

        // A - A = 0
        if (this.pointInverse(b).equals(a)) return Point.INFINITY;

        BigInteger x1 = a.getX();
        BigInteger y1 = a.getY();

        // A + A = 2A
        if (a.equals(b)) {
            BigInteger dx = this.p.mul(x1, x1);
            dx = this.p.mul(dx, BigInteger.valueOf(3));
            dx = this.p.add(dx, this.a);
            BigInteger dy = this.p.mul(y1, BigInteger.valueOf(2));
            BigInteger dyx = this.p.div(dx, dy);
            BigInteger dyx2 = this.p.mul(dyx, dyx);
            BigInteger x2 = this.p.sub(dyx2, this.p.mul(x1, BigInteger.valueOf(2)));
            BigInteger y2 = this.p.sub(x1, x2);
            y2 = this.p.mul(dyx, y2);
            y2 = this.p.sub(y2, y1);
            Point p2 = new Point(x2, y2, 0);
            return p2;
        }

        BigInteger x2 = b.getX();
        BigInteger y2 = b.getY();

        // A + B = C
        BigInteger dy = this.p.sub(y2, y1);     // (y2-y1)
        BigInteger dx = this.p.sub(x2, x1);     // (x2-x1)
        BigInteger dyx = this.p.div(dy, dx);    // (y2-y1)/(x2-x1)
        BigInteger dyx2 = this.p.mul(dyx, dyx); // ((y2-y1)/(x2-x1))^2
        BigInteger x3 = this.p.sub(dyx2, x1);   // ((y2-y1)/(x2-x1))^2 - x1
        x3 = this.p.sub(x3, x2);                // ((y2-y1)/(x2-x1))^2 - x1 - x2
        BigInteger dx31 = this.p.sub(x1, x3);   // (x1-x3)
        BigInteger y3 = this.p.mul(dyx, dx31);  // (y2-y1)/(x2-2x1) * (x1-x3)
        y3 = this.p.sub(y3, y1);                // (y2-y1)/(x2-x1) * (x1-x3) - y1
        Point p3 = new Point(x3, y3, 0);   // (x3, y3)
        return p3;
    }

    // tru 2 diem
    public Point sub(Point a, Point b) {
        return this.sum(a, this.pointInverse(b));
    }

    // nhan 1 diem voi 1 hang so
    public Point mul(Point a, BigInteger n) {
        if (n.compareTo(BigInteger.ZERO) < 0) {
            return this.mul(this.pointInverse(a), n.multiply(BigInteger.valueOf(-1)));
        }
        Point ret = Point.INFINITY;
        while (n.compareTo(BigInteger.ZERO) > 0) {
            if (n.testBit(0))
                ret = this.sum(ret, a);
            n = n.shiftRight(1);
            a = this.sum(a, a);
        }
        return ret;
    }

    // tinh cap cua diem P tren duong cong
    public BigInteger getOrder( Point p )
    {
        BigInteger n = BigInteger.ZERO;
        if( !p.isValid() ) return BigInteger.ZERO;
        Point b = Point.INFINITY;
        do {
            n = n.add(BigInteger.ONE);
            b = this.sum(b,p);
            //System.out.println(n + "P = " + b);
        }
        while ( !b.isInfinity() );
        return n;
    }



    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public BigInteger getB() {
        return b;
    }

    public void setB(BigInteger b) {
        this.b = b;
    }

    public FinitePrimeField getP() {
        return p;
    }

    public void setP(FinitePrimeField p) {
        this.p = p;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }
}
