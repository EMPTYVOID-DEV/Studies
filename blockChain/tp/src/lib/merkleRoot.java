package lib;

import java.util.ArrayList;

public class merkleRoot {
    public static String getRoot(ArrayList<transaction> data) {
        ArrayList<String> root = getHashs(data);
        if (root.size() % 2 != 0) {
            String last = root.getLast();
            root.add(last);
        }
        while (root.size() != 1) {
            ArrayList<String> current = new ArrayList<>();
            for (int i = 0; i < root.size(); i++) {
                if (i % 2 != 0)
                    continue;
                String parentHash = utils.generateDigest(root.get(i) + root.get(i + 1));
                current.add(parentHash);
            }
            root = current;
        }
        return root.getFirst();
    }

    public static ArrayList<String> getHashs(ArrayList<transaction> data) {
        ArrayList<String> hashes = new ArrayList<>();
        for (transaction tran : data) {
            hashes.add(utils.generateDigest(tran.toString()));
        }
        return hashes;
    }
}
