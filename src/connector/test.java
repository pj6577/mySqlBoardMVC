package connector;

import java.sql.Connection;

public class test {
    private Connection conn;
    public test(DBConnector connector) {
        this.conn = connector.makeConnection();
    }
    
    public static void main(String[] args) {
        DBConnector connector = new MYSqlConnector();
            
        test t = new test(connector);
    }
}
