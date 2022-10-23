package RSA;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * giải mã bản mã hóa y thông qua public key (n,e)
 * bằng cách tìm private key d: d*e đồng dư với 1 mod phi n
 * bản rõ x sẽ bằng y^d mod n.
 */
public class Decode {
    public final static BigInteger MINUS1 = new BigInteger("-1", 10);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // s1 là số nguyên y dưới dạng String
        System.out.print("Nhập bản mã : ");
        String s1 = scanner.nextLine();
        BigInteger y = new BigInteger(s1, 10);

        //Nhập public key n và e.
        System.out.println("Nhập public key : ");
        System.out.print("n = ");
        String s2 = scanner.nextLine();
        BigInteger n = new BigInteger(s2, 10);
        System.out.print("e = ");
        String s3 = scanner.nextLine();
        BigInteger e = new BigInteger(s3, 10);
        /*
           Find private key d such that: e*d = 1 mod phi(n)
           p,q are 2 prime factors of n.
           n = p*q.
           p1 = p -1 and q1 = q-1.
         */
        BigInteger p1 = BigInteger.ZERO;
        BigInteger q1 = BigInteger.ZERO;
        // factor n = p*q.
        for (BigInteger i = BigInteger.TWO; i.compareTo(n.sqrt()) <= 0; i = i.add(BigInteger.ONE)) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                System.out.println("n = " + i + " x " + n.divide(i));
                p1 = i.add(MINUS1);
                q1 = n.divide(i).add(MINUS1);
                break;
            }
        }
        // phi(n)
        BigInteger phi = p1.multiply(q1);
        // private key d
        BigInteger d = e.modInverse(phi);
        // Decode x = y^d mod n
        BigInteger x = y.modPow(d, n);
        System.out.println("Bản rõ là x = " + x);

    }
}
