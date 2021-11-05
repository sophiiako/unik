
public class Info {
    private String data;

    public Info() {
        data = "Info about selected firmware";
    }

    public String getInfo() {
        return data;
    }

    public String changeData(String newData) {
        data = newData;
        return data;
    }
}
