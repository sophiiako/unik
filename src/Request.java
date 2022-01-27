import org.firebirdsql.jdbc.FirebirdBlob;
import org.firebirdsql.management.FBCommandLine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLInput;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Request {
    private DBConnector db;
    private static final String tableName = "SOFTWARE";


    public Request(DBConnector dbc) {
        db = dbc;
    }

    public boolean createAddRequest(FirmwareElement f) {
        String request =
                String.format("INSERT INTO %s(firmware, name, md5, branch, birthdate, stage, platform) VALUES " +
                                "(?, \'%s\', \'%s\', \'%s\', \'%s\', \'%s\',\'%s\')",
                        tableName, f.name, f.md5, f.branch,
                        f.date.resultDate, f.stage, f.platform);

            System.out.println(request);

            db.sendRequestWithFW(request, f.binData);

        return true;
    }

    public boolean createDeleteRequest(String md5) {
        String request = String.format("DELETE FROM %s WHERE md5=\'%s\'", tableName, md5);
        System.out.println(request);
        db.sendRequest(request);
        return true;
    }

    public boolean createEditRequest() {

        return true;
    }

    public List<FirmwareElement> createDataRequest(selectFilter filter) {

        String request = String.format("SELECT name, md5, platform, birthdate, branch, stage FROM %s WHERE", tableName);

        for (int i = 0; i < filter.usingPlatforms.size(); ++i) {
            if (i != filter.usingPlatforms.size() - 1) {
                request = String.format("%s platform = \'%s\' OR", request, filter.usingPlatforms.get(i));
            }
            else {
                request = String.format("%s platform = \'%s\'", request, filter.usingPlatforms.get(i));
            } 
        }

        if (filter.onlyRolling) {
            request = String.format("%s AND BRANCH = \'rolling\'", request);
        }
        else if (filter.onlyMaster) {
            request = String.format("%s AND BRANCH = \'master\'", request);
        }
        else if (filter.onlyAnotherBranches) {
            request = String.format("%s AND NOT branch = 'master' AND NOT branch = 'rolling'", request);
        }
        else if (filter.ownBranch != "") {
            request = String.format("%s AND BRANCH = \'%s\'", request, filter.ownBranch);
        }

        request = request +  " order by birthdate";

        System.out.println(request);

        ArrayList<ArrayList<Object>> f = db.getData(request);

        List<FirmwareElement> data = new ArrayList<FirmwareElement>();

        for ( ArrayList<Object> element: f) {
            data.add(new FirmwareElement().setAll((String)element.get(0), (String)element.get(1), (String)element.get(2),
                    element.get(3).toString(),
                    (String)element.get(4), (String)element.get(5)));
        }
        System.out.println(data);
        return data;
    }

    public byte[] getFirmwareByMD5(String md5) {

        String request = String.format("SELECT firmware FROM %s WHERE md5 = \'%s\'", tableName, md5);

        System.out.println(request);

        byte[] frm = db.getFirmware(request);;

        return frm;
    }
}
