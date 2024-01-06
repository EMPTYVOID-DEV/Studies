package app.lib;

public class math {

    public static int elucdian(int a, int b) {
        int res = a % b;
        if (res == 0)
            return b;
        if (res == 1)
            return 1;
        return elucdian(b, res);
    }

    public static int[] extendedEuclideean(int a, int b) {
        if (b == 0) {
            return new int[] { a, 1, 0 };
        } else {
            int[] result = extendedEuclideean(b, a % b);
            int gcd = result[0];
            int x = result[2];
            int y = result[1] - (a / b) * result[2];
            return new int[] { gcd, x, y };
        }
    }

    public static int CRT(Pair<Integer, Integer>[] system) throws Exception {
        int N = 1;
        int res = 0;

        for (int i = 0; i < system.length; i++) {
            for (int j = 0; j < system.length - 1; j++) {
                int gcd = elucdian(system[i].y, system[j].y);
                if (gcd != 1) {
                    throw new Exception("modules are not copirem");
                }
            }
        }

        for (int i = 0; i < system.length; i++) {
            N *= system[i].y;
        }
        for (int i = 0; i < system.length; i++) {
            int Ni = N / system[i].y;

            int yi = extendedEuclideean(system[i].y, Ni)[2];

            res += system[i].x * Ni * yi;
        }
        res = res % N;
        if (res < 0) {
            int div = (int) Math.ceil(res / N);
            res += N * div;
        }
        return res;
    }

    public static int modInverse(int a, int modulo) throws Exception {
        int gcd = math.elucdian(a, modulo);
        if (gcd != 1)
            throw new Exception("This number does not have a inverse");
        else {
            int inv = extendedEuclideean(a, modulo)[1];
            while (inv < 0)
                inv += modulo;
            return inv;
        }
    }

}
