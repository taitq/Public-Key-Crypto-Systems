package ElGamal;

import java.math.BigInteger;
import java.util.Scanner;

public class Decode {
    /**
     * Find a such that: belta = alpha ^ a (mod p).
     * @param belta such that belta = alpha ^ a (mod p).
     * @param alpha is a prime root of p.
     * @param p is a prime number.
     * @return a.
     */
    public static BigInteger discreteLogarithm(BigInteger belta, BigInteger alpha, BigInteger p) {
        for(BigInteger i = BigInteger.ONE; i.compareTo(p.add(BigInteger.valueOf(-1))) <= 0 ; i = i.add(BigInteger.ONE)) {
            if(alpha.modPow(i,p).equals(belta)) {
                return i;
            }
        }
        // if alpha is not prime root of p, there is not probably exist a and return -1;
        return BigInteger.valueOf(-1);
    }
    public static void main(String[] args) {
      //  System.out.println(discreteLogarithm(BigInteger.valueOf(12375),BigInteger.valueOf(106),BigInteger.valueOf(24691)));
     //   System.out.println(discreteLogarithm(BigInteger.valueOf(248388),BigInteger.valueOf(6),BigInteger.valueOf(458009)));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter public key: ");
        System.out.print("p = ");
        String P = scanner.nextLine();
        System.out.print("alpha = ");
        String ALPHA = scanner.nextLine();
        System.out.print("belta = ");
        String BELTA = scanner.nextLine();
        System.out.println("Enter cipher text : ");
        System.out.print("y1 = ");
        String Y1 = scanner.nextLine();
        System.out.print("y2 = ");
        String Y2 = scanner.nextLine();

        // conver string to BigInteger
        BigInteger p = new BigInteger(P);
        BigInteger alpha = new BigInteger(ALPHA);
        BigInteger belta = new BigInteger(BELTA);
        BigInteger y1 = new BigInteger(Y1);
        BigInteger y2 = new BigInteger(Y2);
        // a is discrete logarithm of belta with base alpha in modulo p.
        BigInteger a = discreteLogarithm(belta,alpha,p);
        System.out.println("Private key a = " + a);
        // plain text x = y2 * (y1 ^ a) ^ (-1) = y2 * y1 ^ (p-1-a) mod p
        BigInteger t = p.add(BigInteger.ONE.negate()).add(a.negate());
        System.out.println(t);
        BigInteger temp = y1.modPow(t,p);
        BigInteger x = (y2.multiply(temp)).mod(p);
        System.out.println("Plaintext x = " + x);
    }
}
   /* Enter a prime number p = 5915587277
        A prime root number of n is alpha = 2
        Enter private key a = 5546456532
        Public key belta = 5665780894
        Enter plaintext x = 12346789
        Enter a random integer number k = 56
        Public key (p,alpha,belta) = ( 5915587277 , 2 , 5665780894)
        Private key a = 5546456532
        Cipher text (y1 , y2 ) = ( 2884409246 , 4053173315 )
*/
