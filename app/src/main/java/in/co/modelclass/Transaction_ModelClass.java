package in.co.modelclass;

public class Transaction_ModelClass {

    String datetime,mobileno,paymentid,price,paymentStatues;

    public Transaction_ModelClass(String datetime, String mobileno, String paymentid, String price,String paymentStatues) {
        this.datetime = datetime;
        this.mobileno = mobileno;
        this.paymentid = paymentid;
        this.price = price;
        this.paymentStatues = paymentStatues;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentStatues() {
        return paymentStatues;
    }

    public void setPaymentStatues(String paymentStatues) {
        this.paymentStatues = paymentStatues;
    }
}
