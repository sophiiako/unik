
public class FirmwareElement {
    public String name;
    public String md5;
    public String date;
    public String version;
    public String platform;

    public FirmwareElement() {
        goToInitialValues();
    }

    public void goToInitialValues() {
        name = "unknown";
        md5 = "unknown";
        date = "unknown";
        version = "unknown";
        platform = "unknown";
    }
}
