package in.co.modelclass;

public class FastagRequestHistory_Model {

    String fastagRequestId,numberOffasTag,classoftag,requestedbytype,requestedbyid,status,datetime;

    public FastagRequestHistory_Model(String fastagRequestId, String numberOffasTag, String classoftag,
                                      String requestedbytype, String requestedbyid,String status, String datetime) {

        this.fastagRequestId = fastagRequestId;
        this.numberOffasTag = numberOffasTag;
        this.classoftag = classoftag;
        this.requestedbytype = requestedbytype;
        this.requestedbyid = requestedbyid;
        this.status = status;
        this.datetime = datetime;
    }

    public String getFastagRequestId() {
        return fastagRequestId;
    }

    public void setFastagRequestId(String fastagRequestId) {
        this.fastagRequestId = fastagRequestId;
    }

    public String getNumberOffasTag() {
        return numberOffasTag;
    }

    public void setNumberOffasTag(String numberOffasTag) {
        this.numberOffasTag = numberOffasTag;
    }

    public String getClassoftag() {
        return classoftag;
    }

    public void setClassoftag(String classoftag) {
        this.classoftag = classoftag;
    }

    public String getRequestedbytype() {
        return requestedbytype;
    }

    public void setRequestedbytype(String requestedbytype) {
        this.requestedbytype = requestedbytype;
    }

    public String getRequestedbyid() {
        return requestedbyid;
    }

    public void setRequestedbyid(String requestedbyid) {
        this.requestedbyid = requestedbyid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
