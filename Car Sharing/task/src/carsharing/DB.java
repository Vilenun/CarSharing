package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
    static final String JDBC_DRIVER = "org.h2.Driver";
    Connection conn = null;
    Statement stmt = null;
    public Statement initialize(String name) throws SQLException, ClassNotFoundException {
        String DB_URL = String.format( "jdbc:h2:./src/carsharing/db/%s",name);
        Class.forName(JDBC_DRIVER);
        this.conn = DriverManager.getConnection(DB_URL);
        conn.setAutoCommit(true);
        this.stmt = conn.createStatement();
        return stmt;
    }

    public void close() throws SQLException {
        this.conn.close();
        this.stmt.close();
    };

}
