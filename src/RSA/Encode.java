package RSA;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Mã hóa bản rõ x thành bản mã hóa y bằng hệ mật RSA với public key (n,e) , private key d
 * ed đồng dư với 1 mod phi n
 * bản mã hóa y sẽ bằng x^e mod n.
 */
public class Encode {
    public final static BigInteger MINUS1 = new BigInteger("-1" , 10);

    /**
     * convert char to int in alhabet.
     * @param x char.
     * @return order of x in alphabet table.
     */
    public static int convertAlphabet(char x) {
        return x -'a';
    }

    /**
     * convert a string to number in 26 system.
     * @param s string.
     * @return a number replace to string s.
     */
    public static BigInteger stringToId(String s)
    {
        long id = 0;
        int n = s.length();
        for(int i = n - 1; i >= 0; i--) {
            id += Math.pow(26, n-1-i ) * convertAlphabet(s.charAt(i));
        }
        return new BigInteger(String.valueOf(id),10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // s1 là string cần mã hóa
      /*  System.out.print("Nhập bản rõ : ");
        String s1 = scanner.next();
*/      String t = scanner.next();
        System.out.println(stringToId(t));
        // x là số nguyên cần được mã hóa được convert từ s1
        System.out.print("Nhap x = ");
        String s1 = scanner.next();
        BigInteger x = new BigInteger(s1,10);
        System.out.println("Bản rõ x = " + x);

        // s2 là số nguyên p dưới dạng String
        System.out.print("Nhập số nguyên tố p : ");
        String s2 = scanner.next();

        //s3 là số nguyên q dưới dạng String
        System.out.print("Nhập số nguyên tố q : ");
        String s3 = scanner.next();

        // p,q là 2 số nguyên tố.
        BigInteger p = new BigInteger(s2, 10);
        BigInteger q = new BigInteger(s3, 10);

        // n là mã khóa công khai = p*q.
        BigInteger n = p.multiply(q);

        // phi(n) = (p-1)*(q-1)
        BigInteger p1 = p.add(MINUS1);
        BigInteger q1 = q.add(MINUS1);
        BigInteger phi = p1.multiply(q1);

        // Chọn public key e sao cho gcd(e, phi) = 1.
        // E là dạng string của e
        System.out.print("Nhập e : ");
        String E = scanner.next();
        BigInteger e = new BigInteger(E, 10);

        // private key d sao cho ed đồng dư với 1 mod phi(n).
        BigInteger d = e.modInverse(phi);

        // Bản mã hóa là y = x^e mod n.
        BigInteger y = x.modPow(e, n);
        // signature of x: sigma = x^d
        BigInteger sigma = x.modPow(d,n);
        System.out.println("Signature of x = " + sigma);

        System.out.println("Public key " + " n  = " + n + " and e = " + e);
        System.out.println("Private key d = " + d);
        System.out.println("Bản rõ được mã hóa thành y = " + y);
        System.out.println("Chữ kí được mã hóa thành sigma = " +sigma);
    }
}
/**
 * tranquangtai
 * p =  975319753197531975319
 * q =  10911097110311091151
 * e = 10089886811898868001
 */