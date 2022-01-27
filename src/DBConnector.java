
import org.firebirdsql.jdbc.FBBlob;
import org.firebirdsql.jdbc.FBBlobInputStream;
import org.firebirdsql.jdbc.FBConnection;
import org.firebirdsql.management.FBCommandLine;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;


public class DBConnector
{
    private Connection dbConnection;
    private static DBConnector myConnector ;
    private static final String DRIVER =  "org.firebirdsql.jdbc.FBDriver";
    private static final String URL = "jdbc:firebirdsql:localhost/3050:/dbs/fw.fdb";
    private static final String LOGIN = "SYSDBA";
    private static final String PASSWORD = "456";

    public static synchronized DBConnector getInstance()
    {

        // одиночка!!!
        if (myConnector  == null)
        {
            myConnector  = new DBConnector();
        }
        return myConnector;
    }

    private DBConnector ()
    {
    }

    public void sendRequestWithFW(String request, byte[] fwInBytes) {

        if (dbConnection == null) {
            System.out.println("Connect to database failed! Can not get data.");
            return;
        }
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            dbConnection.setAutoCommit(false);
            st = dbConnection.prepareStatement(request);
            Blob fwBlob = dbConnection.createBlob();
            fwBlob.setBytes(1, fwInBytes);
            st.setBlob(1, (FBBlob)fwBlob);

            int rows = st.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null)
            {
                try
                {
                    st.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }


    public void connect() {
        try
        {
            Class.forName(DRIVER);
            dbConnection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public PreparedStatement getPrepareStatement(String sql)
            throws SQLException {
        return dbConnection.prepareStatement(sql);
    }

    public void sendRequest(String request) {
        if (dbConnection == null) {
            System.out.println("Connect to database failed! Can not get data.");
            return;
        }
        Statement st = null;
        ResultSet rs = null;

        try {
            st = dbConnection.createStatement();
            int rows = st.executeUpdate(request);
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null)
            {
                try
                {
                    st.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized ArrayList<ArrayList<Object>> getData(String query)
    {
        System.out.println(query);
        ArrayList<ArrayList<Object>> dataVector = new ArrayList<ArrayList<Object>>();

        if (dbConnection == null) {
            System.out.println("Connect to database failed! Can not get data.");
            return dataVector;
        }
        Statement st = null;
        ResultSet rs = null;
        try
        {
            st = dbConnection.createStatement();
            rs = st.executeQuery(query);
            int columns = rs.getMetaData().getColumnCount();

            while (rs.next())
            {
                ArrayList<Object> nextRow = new ArrayList<Object>(columns);

                for (int i = 1; i <= columns; i++)
                {
                    nextRow.add(rs.getObject(i));

                }
                dataVector.add(nextRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (st != null)
            {
                try
                {
                    st.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(dataVector);
        return dataVector;
    }

    public synchronized byte[] getFirmware(String query)
    {
        System.out.println(query);

        byte[] resultInBytes;

        if (dbConnection == null) {
            System.out.println("Connect to database failed! Can not get data.");
            return new byte[0];
        }
        Statement st = null;
        ResultSet rs = null;
        try
        {
            st = dbConnection.createStatement();
            rs = st.executeQuery(query);
            int columns = rs.getMetaData().getColumnCount();

            Blob resultInBlob = dbConnection.createBlob();
            rs.next();
            System.out.println(rs.getObject(1));
            resultInBlob = rs.getBlob(1);

            resultInBytes = resultInBlob.getBytes(1, (int)resultInBlob.length());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (st != null)
            {
                try
                {
                    st.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        //System.out.println(dataVector);
        return new byte[0];
    }

    public void release()
    {
        if (myConnector  != null)
        {
            myConnector  = null;
        }
        if (dbConnection != null)
        {
            try
            {
                dbConnection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}

