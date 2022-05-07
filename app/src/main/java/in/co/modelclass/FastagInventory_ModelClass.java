package in.co.modelclass;

public class FastagInventory_ModelClass {

    String BAR_CODE,TAG_ID,CLASS_OF_TAG,STATUS;

    public FastagInventory_ModelClass(String BAR_CODE, String TAG_ID, String CLASS_OF_TAG, String STATUS) {
        this.BAR_CODE = BAR_CODE;
        this.TAG_ID = TAG_ID;
        this.CLASS_OF_TAG = CLASS_OF_TAG;
        this.STATUS = STATUS;
    }

    public String getBAR_CODE() {
        return BAR_CODE;
    }

    public void setBAR_CODE(String BAR_CODE) {
        this.BAR_CODE = BAR_CODE;
    }

    public String getTAG_ID() {
        return TAG_ID;
    }

    public void setTAG_ID(String TAG_ID) {
        this.TAG_ID = TAG_ID;
    }

    public String getCLASS_OF_TAG() {
        return CLASS_OF_TAG;
    }

    public void setCLASS_OF_TAG(String CLASS_OF_TAG) {
        this.CLASS_OF_TAG = CLASS_OF_TAG;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
