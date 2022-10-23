package ECC;

import java.math.BigInteger;

public class test {
    public static void main(String[] args) {
        BigInteger p = new BigInteger("14734520141266665763");
        FinitePrimeField finitePrimeField = new FinitePrimeField(p);
        PrimeCurve primeCurve = new PrimeCurve(finitePrimeField, BigInteger.ONE, BigInteger.ONE, 1);
        // message m
        BigInteger m = new BigInteger("3683630035316666441");
        // Private Key s
        BigInteger s = new BigInteger("947");
        // random number
        BigInteger k = EC_ELGamal.randomBigInteger();
        // public key B, P :
        Point P = new Point(72, 611, 0);
        Point B = primeCurve.mul(P, s);
        Point[] ciphertext = EC_ELGamal.ElGamalEnc(primeCurve, m, B, P);
        System.out.println(ciphertext[0].toString());
        System.out.println(ciphertext[1].toString());

    }
}
