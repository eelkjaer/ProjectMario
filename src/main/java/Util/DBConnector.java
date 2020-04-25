/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("FieldCanBeLocal")
public class DBConnector {

    private Connection connection;
    private static DBConnector instance;

    private final String serverIP = "104.248.135.65";
    private final String serverPort = "3306";
    private final String serverUsr = "java";
    private final String serverPsw = "java";
    private final String serverDb = "mariodb";

    private DBConnector() {
        try {
            String baseurl = "jdbc:mysql://" + serverIP + ":" + serverPort + "/";
            String timeZ = "serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String noSSL = "&useSSL=false";
            timeZ += noSSL;
            String totalUrl = baseurl+serverDb+"?"+timeZ;
            connection = DriverManager.getConnection(totalUrl,serverUsr,serverPsw);
        } catch (SQLException id) {
            System.out.println("Wrong " + id.getMessage());
        }
    }
    public static DBConnector getInstance() {
        if (instance == null ) {
            instance = new DBConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }
}