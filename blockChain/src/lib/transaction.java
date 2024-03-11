package lib;

public class transaction {
    String sender;
    String receiver;
    float amount;
    float fee;
    String sign;

    public String infoString() {
        return this.sender + this.receiver + this.amount + this.fee;
    }

    public transaction(String sender, String receiver, float amount, float fee) throws Exception {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.fee = fee;
        sign = new signature(this.infoString()).toString();
    }

    @Override
    public String toString() {
        return infoString() + this.sign;
    }

}
