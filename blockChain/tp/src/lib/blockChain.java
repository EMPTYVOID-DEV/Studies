package lib;

import java.util.ArrayList;

public class blockChain {
    private ArrayList<block> chain;

    public blockChain(ArrayList<transaction> data) {
        this.chain = new ArrayList<block>();
        this.createGenesisBlock(data);
    }

    public void add(ArrayList<transaction> data) {
        block lastBlock = this.chain.getLast();
        block newBlock = new block(lastBlock.getHash(), data);
        this.chain.add(newBlock);
    }

    public void createGenesisBlock(ArrayList<transaction> data) {
        block genesis = new block("", data);
        this.chain.add(genesis);
    }

    public void print() {
        for (int i = 0; i < this.chain.size(); i++) {
            System.err.println("The block number " + i);
            System.out.println(this.chain.get(i).toString());
        }
    }

}
