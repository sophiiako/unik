import java.util.ArrayList;
import java.util.List;

public class Request {
    private DBConnector db;


    public Request(DBConnector dbc) {
        db = dbc;
    }

    public boolean createAddRequest(FirmwareElement f) {
        db.add(f);
        return true;
    }

    public boolean createDeleteRequest(String name) {
        db.delete(name);
        return true;
    }

    public boolean createEditRequest() {
        return true;
    }

    public List<String> createSearchRequest() {
        List<FirmwareElement> f = db.getAll();
        List<String> data = new ArrayList<String>();
        for (FirmwareElement e : f) {
            data.add(e.name);
        }
        return data;
    }
    public String createSearchRequestByName(String name) {
        FirmwareElement element = db.getByName(name);
        return "date:\n" + element.date.resultDate + "\nmd5:\n" + element.md5 + "\nplatform:\n"
                                                     + element.platform + "\nversion:\n" + element.version + "\n";
    }
}
