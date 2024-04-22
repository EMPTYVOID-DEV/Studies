package lib;

import java.util.ArrayList;

public class block {
    private int nonce;
    private Long timestamp;
    private String hash;
    private String previousHash;
    private String merkleHash;
    private ArrayList<transaction> data;

    public block(String previousHash, ArrayList<transaction> data) {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.merkleHash = merkleRoot.getRoot(data);
        this.mining();
    }

    // the puzzle is to start with one zero
    public void mining() {
        int nonce = 0;
        String hash = "";
        while (true) {
            hash = utils.generateDigest(getBlockHeader(nonce));
            if (hash.charAt(0) == '0')
                break;
            nonce++;
        }
        this.hash = hash;
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "block [nonce=" + nonce + ", timestamp=" + timestamp + ", hash=" + hash + ", previousHash="
                + previousHash + ", merkleHash=" + merkleHash + ", data=" + data.toString() + "]";
    }

    public String getBlockHeader(int nonce) {
        return this.merkleHash + this.timestamp + nonce + this.previousHash;
    }

    public String getHash() {
        return hash;
    }

}
