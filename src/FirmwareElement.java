
public class FirmwareElement {
    public String name;
    public String md5;
    public Date date;
    public String version;
    public String platform;

    public FirmwareElement() {
        goToInitialValues();
    }

    public void goToInitialValues() {
        name = "";
        md5 = "";
        date = new Date("");
        version = "";
        platform = "";
    }
}
