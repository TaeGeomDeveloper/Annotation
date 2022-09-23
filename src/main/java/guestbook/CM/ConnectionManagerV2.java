package guestbook.CM;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManagerV2 {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Context initCtx = new InitialContext();
            Context ctx = (Context) initCtx.lookup("java:/comp/env");
            DataSource ds = (DataSource)ctx.lookup("jdbc/gntp");
            con = ds.getConnection();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return con;
    }

//    public static Connection getConnetionOld() {
//        Connection con = null;
//        String driver = "com.mysql.cj.jdbc.Driver";
//        String jdbcURL = "jdbc:mysql://localhost:3306/gntp";
//        String id = "root";
//        String pwd = "1234";
//
//        try {
//            Class.forName(driver);
//            con = DriverManager.getConnection(jdbcURL, id, pwd);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return con;
//    }


    public static void closeConnection(ResultSet rs, Statement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
