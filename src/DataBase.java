

import java.sql.*;
import java.util.Objects;


public class DataBase {

    static final String db_url = "jdbc:postgresql://10.227.240.130:5432/pswa0603";
    static final String db_user   = "pswa0603";
    static final String passwd = "cyberwood";

    public static int numberOfDataBaseQuerys = 0;

    public static void readOrder(){
        numberOfDataBaseQuerys++;
        String query  = "SELECT * FROM infi.orders";
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                System.out.print("ID: "          + rs.getInt("id"));
                System.out.print(", user: " + rs.getString("name"));
                System.out.print(", password: "      + rs.getString("number"));
                System.out.print(", email: "      + rs.getString("workpiece"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertOrder(String name, String Number, String WP, String Quantity, String DueDate, String EP, String LP){
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query  = "INSERT INTO infi.orders (name, number, workpiece, quantity, duedate, latepen, earlypen) " + "VALUES ('"+ name + "', '" + Number + "', '" + WP + "', '" + Quantity + "', '" + DueDate+ "', '" + EP+ "', '" + LP + "');";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return 1;
    }

    public static int updateOrder(String Number, boolean done){
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query = "UPDATE infi.orders " + "SET done = '" + done + "' " + "WHERE number = '" + Number + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return 1;

    }
}