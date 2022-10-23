package ECC;

import java.math.BigInteger;
import java.util.Random;

public class EC_ELGamal {

    // Ma hoa EC-Elgamal

    /**
     *
     * @param elipticCurve duong cong eliptic.
     * @param m message
     * @param B public key Point, B = s*P.
     * @param P public key point diem sinh.
     * @return cipher text (M1,M2).
     */
    public static Point[] ElGamalEnc(PrimeCurve elipticCurve, BigInteger m, Point B, Point P) {
        Point M = EC_ELGamal.KoblitzEnc(elipticCurve, m);
        BigInteger k = EC_ELGamal.randomBigInteger();
        Point M1 = elipticCurve.mul(P, k);
        Point temp = elipticCurve.mul(B, k);
        Point M2 = elipticCurve.sum(M,temp );
        return new Point[]{M1, M2};
    }

    // Giai ma EC_ELGamal
    public static BigInteger ElGamalDec(PrimeCurve P, Point B, Point[] VW, BigInteger nD) {
        Point V = VW[0];
        Point W = VW[1];
        Point nDV = P.mul(V, nD);
        Point Pm = P.sub(W, nDV);
        BigInteger m = EC_ELGamal.KoblitzDec(P, Pm);
        return m;
    }


    public static BigInteger randomBigInteger() {
       /* BigInteger n = new BigInteger("12345678987654321");
        Random rnd = new Random();
        int bitLength = n.bitLength();
        BigInteger ret;
        do {
            ret = new BigInteger(bitLength, rnd);
        } while (ret.compareTo(n) > 0);
        return ret;*/
        return BigInteger.valueOf(97742);
    }

    // Ma hoa Koblitz thong diep gui di thanh 1 diem tren duong cong
    public static Point KoblitzEnc(PrimeCurve P, BigInteger m) {
       /* Integer h = P.getH();
        BigInteger x = m.multiply(BigInteger.valueOf(h)); // x = m*h
        for (int i = 0; i < h; i++) {
            Point p = P.getPoint(x);
            if (p.isValid()) return p;
            x = x.add(BigInteger.ONE);
        }
        return Point.INVALID;*/
        return P.getPoint(m);
    }

    // giai ma Koblitz tu 1 diem tren duong cong sang thong diep
    public static BigInteger KoblitzDec(PrimeCurve P, Point p) {
        Integer h = P.getH();
        BigInteger x = p.getX().divide(BigInteger.valueOf(h));
        return x;
    }

}