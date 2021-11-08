
public class Info {
    private String data;

    public Info() {
        data = initialPanel();
    }

    public String getInfo() {
        return data;
    }

    public String changeData(String newData) {
        data = "\n" + newData;
        return data;
    }

    public String initialPanel() {
        data = "Info about selected firmware";
        return data;
    }
}
