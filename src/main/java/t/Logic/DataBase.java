package t.Logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    public static int insertOrder(String name, String Number, String WP, String Quantity, String DueDate, String EP, String LP, String state){
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query  = "INSERT INTO infi.orders (name, number, workpiece, quantity, duedate, latepen, earlypen, state) " + "VALUES ('"+ name + "', '" + Number + "', '" + WP + "', '" + Quantity + "', '" + DueDate+ "', '" + LP+ "', '" + EP + "', '" + state + "');";

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

    public static List<Order> uploadUnfinishedOrders(){
        numberOfDataBaseQuerys++;

        List<Order> orders = new ArrayList<>();

        // Connect to the DBMS (DataBase Management Server)
        String query  = "SELECT * FROM infi.orders WHERE state = 'WAITING' OR state = 'PROCESSING' ;";
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                orders.add(new Order(rs.getString("number"),
                        rs.getString("workpiece"),
                        rs.getString("quantity"),
                        rs.getString("duedate"),
                        rs.getString("latepen"),
                        rs.getString("earlypen"),
                        rs.getString("state")));
            }

            if(orders.size() == 0){
                return null;
            }

            return orders;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int updateStock(String WorkPiece, int Quantity){
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query = "UPDATE infi.stock " + "SET quantity = '" + Quantity + "' " + "WHERE workpiece = '" + WorkPiece + "';";

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

    public static int getStock(String WorkPiece){
        numberOfDataBaseQuerys++;
        String query  = "SELECT quantity FROM infi.stock WHERE workpiece = '" + WorkPiece + "';";
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public static int deleteMPS() {
        // DELETE FROM table_name;
        numberOfDataBaseQuerys++;
        String query = "DELETE FROM \"infi\".\"MPS\";";
        try (Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if (res == 0) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return 1;
    }

    public static void insertMPS(int day, int cP1, int cP2, int pP3, int pP4, int pP5, int pP6, int pP7, int pP8, int pP9, int d3, int d4, int d5, int d6, int d7, int d8, int d9){
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query = "INSERT INTO \"infi\".\"MPS\" (\"day\", \"comingP1\", \"comingP2\", " +
                "\"productionP3\", \"productionP4\", \"productionP5\", \"productionP6\", \"productionP7\", \"productionP8\", \"productionP9\", " +
                "\"deliverP3\", \"deliverP4\", \"deliverP5\", \"deliverP6\", \"deliverP7\", \"deliverP8\", \"deliverP9\") " +
                "VALUES (" + day + ", " + cP1 + ", " + cP2 + ", " +
                pP3 + ", " + pP4 + ", " + pP5 + ", " + pP6 + ", " + pP7 + ", " + pP8 + ", " + pP9 + ", " +
                d3 + ", " + d4 + ", " + d5 + ", " + d6 + ", " + d7 + ", " + d8 + ", " + d9 + ");";


        //System.out.println(query);

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertMPSBatch(Day[] daysClass) {
        numberOfDataBaseQuerys++;
        String query = "INSERT INTO \"infi\".\"MPS\" (\"day\", \"comingP1\", \"comingP2\", " +
                "\"productionP3\", \"productionP4\", \"productionP5\", \"productionP6\", \"productionP7\", \"productionP8\", \"productionP9\", " +
                "\"deliverP3\", \"deliverP4\", \"deliverP5\", \"deliverP6\", \"deliverP7\", \"deliverP8\", \"deliverP9\") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (Day day : daysClass) {
                stmt.setInt(1, day.getDay());
                stmt.setInt(2, day.getComingP1());
                stmt.setInt(3, day.getComingP2());
                stmt.setInt(4, day.getProductionP3());
                stmt.setInt(5, day.getProductionP4());
                stmt.setInt(6, day.getProductionP5());
                stmt.setInt(7, day.getProductionP6());
                stmt.setInt(8, day.getProductionP7());
                stmt.setInt(9, day.getProductionP8());
                stmt.setInt(10, day.getProductionP9());
                stmt.setInt(11, day.getDeliverP3());
                stmt.setInt(12, day.getDeliverP4());
                stmt.setInt(13, day.getDeliverP5());
                stmt.setInt(14, day.getDeliverP6());
                stmt.setInt(15, day.getDeliverP7());
                stmt.setInt(16, day.getDeliverP8());
                stmt.setInt(17, day.getDeliverP9());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
