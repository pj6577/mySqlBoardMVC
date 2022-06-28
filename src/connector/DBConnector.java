package connector;

import java.sql.Connection;

public interface DBConnector {
    public Connection makeConnection();
}
