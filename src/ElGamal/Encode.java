package ElGamal;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;

public class Encode {
    /**
     * @param n a number that need to be checked.
     * @return true if n is a prime and false in the other.
     */
    public static boolean checkPrime(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) < 0) {
            return false;
        }
        if (n.compareTo(BigInteger.TWO) == 0 || n.compareTo(BigInteger.valueOf(3)) == 0) {
            return true;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO) || n.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO)) {
            return false;
        }
        for (BigInteger i = BigInteger.TWO; i.multiply(i).compareTo(n) <= 0; i = i.add(BigInteger.valueOf(6))) {
            if (n.mod(i).equals(BigInteger.ZERO) || n.mod(i.add(BigInteger.TWO)).equals(BigInteger.ZERO)) {
                return false;
            }
        }
        return true;
    }

    /**
     * factor prime of n.
     *
     * @param setPrimeFactor set of prime factors of n.
     * @param n              is a integer number.
     */
    public static void findPrimeFactor(HashSet<BigInteger> setPrimeFactor, BigInteger n) {
        while (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            setPrimeFactor.add(BigInteger.TWO);
            n = n.divide(BigInteger.TWO);
        }
        for (BigInteger i = BigInteger.valueOf(3); i.multiply(i).compareTo(n) <= 0; i = i.add(BigInteger.TWO)) {
            while (n.mod(i).equals(BigInteger.ZERO)) {
                setPrimeFactor.add(i);
                n = n.divide(i);
            }
        }
        if (n.compareTo(BigInteger.TWO) > 0) {
            setPrimeFactor.add(n);
        }
    }


    /**
     * @param p is a prime number.
     * @return the smallest prime root of n.
     */
    public static BigInteger findPrimitiveRoot(BigInteger p) {
       /* if (!checkPrime(p)) {
            System.out.println("p is not a prime number. Enter again.");
            return BigInteger.valueOf(-1);
        }*/

        HashSet<BigInteger> setPrimeFactor = new HashSet<>();
        BigInteger phi = p.add(BigInteger.valueOf(-1));
        findPrimeFactor(setPrimeFactor, phi);
            for (BigInteger i = BigInteger.TWO; i.compareTo(phi) <= 0; i = i.add(BigInteger.ONE)) {
            boolean check = false;
            for (BigInteger k : setPrimeFactor) {
                // check ( i^(phi/p_i) = 1 [p] ) every i if yes check = true and i is not prime root of p.
                if (i.modPow(phi.divide(k), p).equals(BigInteger.ONE)) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                return i;
            }
        }
        return BigInteger.valueOf(-1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a prime number p = ");
        String P = scanner.nextLine();
        // p is a prime number
        BigInteger p = new BigInteger(P, 10);
        // alpha is the smallest primitive root of p
        BigInteger alpha = findPrimitiveRoot(p);
        System.out.println("A prime root number of n is alpha = " + alpha);
        System.out.print("Enter private key a = ");
        String A = scanner.nextLine();
        BigInteger a = new BigInteger(A);
        BigInteger belta = alpha.modPow(a, p);
        System.out.println("Public key belta = " + belta);
        System.out.print("Enter plaintext x = ");
        String X = scanner.nextLine();
        BigInteger x = RSA.Encode.stringToId(X);
        System.out.println(x);// 17837782261913368429 413431564738954 phamkhacdat
        System.out.print("Enter a random integer number k = ");
        String K = scanner.nextLine();
        BigInteger k = new BigInteger(K);
        BigInteger y1 = alpha.modPow(k, p);
        BigInteger y2 = (x.multiply(belta.modPow(k, p))).mod(p);
        System.out.println("Public key (p,alpha,belta) = ( " + p + " , " + alpha + " , " + belta + ")");
        System.out.println("Private key a = " + a);
        System.out.println("Cipher text (y1 , y2 ) = ( " + y1 + " , " + y2 + " ) ");
    }
}
