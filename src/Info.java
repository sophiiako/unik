
public class Info {
    private String data;

    public Info() {
        data = initialPanel();
    }

    public String getInfo() {
        return data;
    }

    public String changeData(String newData) {
        // method to make cute format to panel
        data = newData;
        return data;
    }

    public String initialPanel() {
        //data = "Info about selected firmware";
        data = "the field for information about platform, date, version and etc";
        return data;
    }
}
