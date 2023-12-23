package lib;

import java.math.BigInteger;
import java.security.SecureRandom;

public class customRsa {

    public static void main(String[] args) throws Exception {
        BigInteger p = new BigInteger("47");
        BigInteger q = new BigInteger("31");
        Pair<BigInteger, BigInteger> key = keyGen(p, q);
        byte[] msg = "R".getBytes();
        BigInteger n = p.multiply(q);
        BigInteger cipher = encrypt(key.x, n, new BigInteger(msg));
        BigInteger decryptedMsg = decrypt(key.y, n, cipher);
        utils.print("" + new BigInteger(msg));
        utils.print("" + cipher);
        utils.print("" + decryptedMsg);
    }

    public static BigInteger encrypt(BigInteger e, BigInteger n, BigInteger msg) throws Exception {
        if (msg.compareTo(n) >= 0)
            throw new Exception("Large message");
        return msg.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger d, BigInteger n, BigInteger cipher) {
        return cipher.modPow(d, n);
    }

    public static BigInteger random(BigInteger min, BigInteger max) {
        if (min.compareTo(max) >= 0) {
            throw new IllegalArgumentException("min must be less than max");
        }
        BigInteger range = max.subtract(min).add(BigInteger.ONE);
        SecureRandom random = new SecureRandom();
        BigInteger randomValue = new BigInteger(range.bitLength(), random);
        while (randomValue.compareTo(range) >= 0) {
            randomValue = new BigInteger(range.bitLength(), random);
        }
        return randomValue.add(min);
    }

    public static Pair<BigInteger, BigInteger> keyGen(BigInteger p, BigInteger q) {
        BigInteger phin = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = random(BigInteger.ONE, phin);
        while (!phin.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        BigInteger d = e.modInverse(phin);
        return new Pair<BigInteger, BigInteger>(e, d);
    }

    public static BigInteger squareMultiply(BigInteger base, BigInteger exponent, BigInteger modulo) {
        BigInteger result = BigInteger.ONE;
        base = base.mod(modulo);
        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.testBit(0)) {
                result = result.multiply(base).mod(modulo);
            }
            base = base.multiply(base).mod(modulo);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    public static boolean fermatIsPrime(BigInteger n, int max_iterations) {
        for (int i = 1; i <= max_iterations; i++) {
            BigInteger I = new BigInteger(Integer.toString(i));
            if (!squareMultiply(I, n, n).equals(I))
                return false;
        }
        return true;
    }
}
