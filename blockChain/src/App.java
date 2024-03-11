import java.util.ArrayList;
import java.util.List;
import lib.blockChain;
import lib.transaction;

public class App {
    public static void main(String[] args) throws Exception {
        transaction a = new transaction("nazim", "milzlz", 200, 20);
        transaction b = new transaction("nazim", "milzlz", 200, 20);
        transaction c = new transaction("nazim", "milzlz", 200, 20);
        transaction d = new transaction("nazim", "milzlz", 200, 20);
        ArrayList<transaction> lol = new ArrayList<>(List.of(a, b, c, d));
        blockChain bitcoin = new blockChain(lol);
        bitcoin.add(lol);
        bitcoin.add(lol);
        bitcoin.print();
    }

    public static void print(String msg) {
        System.out.println(msg);
    }
}
