package in.co.modelclass;

public class ClassoFtag_ModelClass {

    String classoFtag,fastagprice,productid;

    public ClassoFtag_ModelClass(String classoFtag) {
        this.classoFtag = classoFtag;
    }

    public ClassoFtag_ModelClass(String classoFtag, String fastagprice, String productid) {
        this.classoFtag = classoFtag;
        this.fastagprice = fastagprice;
        this.productid = productid;
    }

    public String getClassoFtag() {
        return classoFtag;
    }

    public void setClassoFtag(String classoFtag) {
        this.classoFtag = classoFtag;
    }

    public String getFastagprice() {
        return fastagprice;
    }

    public void setFastagprice(String fastagprice) {
        this.fastagprice = fastagprice;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    @Override
    public String toString() {
        return "ClassoFtag_ModelClass{" +
                "classoFtag='" + classoFtag + '\'' +
                ", fastagprice='" + fastagprice + '\'' +
                '}';
    }
}
