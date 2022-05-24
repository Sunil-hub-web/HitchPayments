package in.co.modelclass;

public class Wallet_ModelClass {

    String walletid,payerid,payertype,amount,transactionid,transactiontype,transactionstatus,txn,datetime;

    public Wallet_ModelClass(String walletid, String payerid, String payertype, String amount,
                             String transactionid, String transactiontype, String transactionstatus,
                             String txn, String datetime) {

        this.walletid = walletid;
        this.payerid = payerid;
        this.payertype = payertype;
        this.amount = amount;
        this.transactionid = transactionid;
        this.transactiontype = transactiontype;
        this.transactionstatus = transactionstatus;
        this.txn = txn;
        this.datetime = datetime;
    }

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public String getPayerid() {
        return payerid;
    }

    public void setPayerid(String payerid) {
        this.payerid = payerid;
    }

    public String getPayertype() {
        return payertype;
    }

    public void setPayertype(String payertype) {
        this.payertype = payertype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getTransactionstatus() {
        return transactionstatus;
    }

    public void setTransactionstatus(String transactionstatus) {
        this.transactionstatus = transactionstatus;
    }

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
