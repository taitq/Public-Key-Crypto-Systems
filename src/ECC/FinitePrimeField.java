package ECC;

import java.math.BigInteger;

public class FinitePrimeField {
    private BigInteger p;
    private Integer sqrtAlg = 0;

    public FinitePrimeField(BigInteger p) {
        this.p = p;
        // tinh p mod 8 de su dung 3 thuat toan khac nhau cho viec tim sqrt cua p
        BigInteger remained = this.p.mod(BigInteger.valueOf(8));
        switch (remained.intValue()) {
            case 3, 7 -> this.sqrtAlg = 1;
            case 5 -> this.sqrtAlg = 2;
            default -> this.sqrtAlg = 0;
        }
    }

    public FinitePrimeField(int p) {
        this(BigInteger.valueOf(p));
    }


    public BigInteger mod(BigInteger x) {
        return x.mod(this.p);
    }


    public boolean isResidue(BigInteger a) {
        a = a.mod(this.p);
        // a is Residue if only if : a^(p-1)/2 = 1 mod p

        BigInteger exp = this.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
        return a.modPow(exp, this.p).compareTo(BigInteger.ONE) == 0;
    }

    public boolean isResidue(Integer a) {
        return this.isResidue(BigInteger.valueOf(a));
    }


    public BigInteger sqrt(BigInteger a) {
        BigInteger ret = null;
        a = a.mod(this.p);
        // check a is Residue
        if (this.isResidue(a)) {
            // if p = 3 (mod 4)
            if (this.sqrtAlg == 1) {
                // sqrt(a) = a^(p+1)/4 mod p
                BigInteger exp = this.p.add(BigInteger.ONE).divide(BigInteger.valueOf(4));
                ret = a.modPow(exp, this.p);
            }
            //  p = 5 (mod 8)
            else if (this.sqrtAlg == 2) {
                // xet 2 truong hop  cua a^(p-1)/4 mod p
                BigInteger discriminante = this.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(4));
                discriminante = a.modPow(discriminante, this.p);
                if (discriminante.compareTo(BigInteger.ONE) == 0) {
                    // if = 1 mod p
                    // then sqrt(a) = a^(p+3)/8 mod p
                    BigInteger exp = this.p.add(BigInteger.valueOf(3)).divide(BigInteger.valueOf(8));
                    ret = a.modPow(exp, this.p);
                } else {
                    // if = -1 mod p
                    // then sqrt(a) = 2a * (4a)^((p-5)/8) mod p
                    BigInteger exp = this.p.subtract(BigInteger.valueOf(5)).divide(BigInteger.valueOf(8));
                    BigInteger a2 = a.multiply(BigInteger.valueOf(2));
                    BigInteger a4 = a.multiply(BigInteger.valueOf(4));
                    ret = a4.modPow(exp, this.p).multiply(a2);
                }
            }
            // if p = 1 (mod 8)
            else {
                // algoritmo di Tonelli-Shanks
                ret = this.tonelliShanks(a);
            }
            if (ret != null)
                ret = ret.mod(this.p);
        }
        return ret;
    }

    private BigInteger tonelliShanks(BigInteger n) {
        BigInteger s = BigInteger.ZERO;
        BigInteger q = this.p.subtract(BigInteger.ONE);
        n = n.mod(this.p);
        // Find Q and s:  (p-1) = 2^S * Q
        while (!q.testBit(0)) {
            q = q.shiftRight(1);
            s = s.add(BigInteger.ONE);
        }

        // truong hop dac biet s = 1
        if (s.compareTo(BigInteger.ONE) == 0) {
            BigInteger exp = this.p.add(BigInteger.ONE).divide(BigInteger.valueOf(4));
            BigInteger r = n.modPow(exp, this.p);
            if (r.pow(2).mod(this.p).compareTo(n) == 0) return r;
            else return null;
        }
        // tim so nguyen z dau tien khong phai la thang du bac hai
        BigInteger z = BigInteger.ONE;
        do {
            z = z.add(BigInteger.ONE);
        } while (this.isResidue(z));

        // c = z^q mod p
        BigInteger c = z.modPow(q, this.p);
        // r = n^( (q+1)/2 ) mod p
        BigInteger expR = q.add(BigInteger.ONE).divide(BigInteger.valueOf(2));
        BigInteger r = n.modPow(expR, this.p);
        // t = n^q mod p
        BigInteger t = n.modPow(q, this.p);
        // m = s
        BigInteger m = s;

        while (t.compareTo(BigInteger.ONE) != 0) {
            BigInteger tt = t;
            BigInteger i = BigInteger.ZERO;
            while (tt.compareTo(BigInteger.ONE) != 0) {
                tt = tt.multiply(tt).mod(this.p);
                i = i.add(BigInteger.ONE);
                if (i.compareTo(m) == 0) return null;
            }
            // b = c^( 2^(M-i-1) ) mod p
            BigInteger exp2 = m.subtract(i).subtract(BigInteger.ONE);
            BigInteger expB = BigInteger.valueOf(2).modPow(exp2, this.p.subtract(BigInteger.ONE));
            BigInteger b = c.modPow(expB, this.p);
            // bb = b^2 mod p
            BigInteger bb = b.pow(2).mod(this.p);
            // r = r*b mod p
            r = r.multiply(b).mod(this.p);
            // t = t*bb mod p
            t = t.multiply(bb).mod(this.p);
            c = bb;
            m = i;
        }
        if (r.pow(2).mod(this.p).compareTo(n) == 0) return r;
        return null;
    }


    public BigInteger opposite(BigInteger x) {
        x = x.mod(this.p);
        return this.p.subtract(x).mod(this.p);
    }

    public BigInteger mul(BigInteger x, BigInteger y) {
        x = x.mod(this.p);
        y = y.mod(this.p);
        return x.multiply(y).mod(p);
    }

    public BigInteger mul(Integer x, Integer y) {
        return this.mul(BigInteger.valueOf(x),BigInteger.valueOf(y));
    }

    public BigInteger add(Integer x, Integer y) {
        return this.add(BigInteger.valueOf(x), BigInteger.valueOf(y));
    }

    public BigInteger add(BigInteger x, BigInteger y) {
        x = x.mod(p);
        y = y.mod(p);
        return x.add(y).mod(this.p);
    }

    public BigInteger div(BigInteger x, BigInteger y) {
        x = x.mod(p);
        y = y.mod(p);
        return this.mul(x, this.inverse(y));
    }

    public BigInteger inverse(BigInteger a) {
        a = a.mod(this.p);
        BigInteger ret = null;
        try {
            ret = a.modInverse(this.p);
        } catch (ArithmeticException e) {
            ret = null;
        }
        return ret;
    }

    public BigInteger inverse(Integer a) {
        return this.inverse(BigInteger.valueOf(a));
    }

    public BigInteger div(Integer x, Integer y) {
        return this.div(BigInteger.valueOf(x), BigInteger.valueOf(y));
    }

    public BigInteger sub(BigInteger x, BigInteger y) {
        x = x.mod(this.p);
        y = y.mod(this.p);
        return x.subtract(y).mod(this.p);
    }

    public BigInteger sub(Integer x, Integer y) {
        return this.sub(BigInteger.valueOf(x),BigInteger.valueOf(y));
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }
}
