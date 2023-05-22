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

    public static void loadOrders(){
        numberOfDataBaseQuerys++;
        String query  = "SELECT * FROM infi.orders";
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("OrderNumber: "          + rs.getInt("number"));
                System.out.println("WorkPiece: " + rs.getString("workpiece"));
                System.out.println("Quantity: "      + rs.getString("quantity"));
                System.out.println("DueDate: "      + rs.getString("duedate"));
                System.out.println("EarlyPen: "      + rs.getString("earlypen"));
                System.out.println("LatePen: "      + rs.getString("latepen"));
                System.out.println("state: "      + rs.getString("state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> loadAllOrders() {
        numberOfDataBaseQuerys++;
        String query = "SELECT * FROM infi.orders";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Analyze the resulting data
            while (rs.next()) {
                String name = rs.getString("name");
                String orderNumber = rs.getString("number");
                String workPiece = rs.getString("workpiece");
                String quantity = rs.getString("quantity");
                String dueDate = rs.getString("duedate");
                String earlyPen = rs.getString("earlypen");
                String latePen = rs.getString("latepen");
                String state = rs.getString("state");
                String realduedate = rs.getString("realduedate");
                String pd = rs.getString("purchasing_day");
                String pq = rs.getString("purchasing_quantity");

                Order order = new Order(name, orderNumber, workPiece, quantity, dueDate, latePen, earlyPen, state, realduedate, pd, pq);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
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

    public static void deleteOrders() {
        // DELETE FROM table_name;
        numberOfDataBaseQuerys++;
        String query = "DELETE FROM infi.orders;";
        try (Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateOrderRealDueDate(String realduedate, String Number){
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query = "UPDATE infi.orders " + "SET realduedate = '" + realduedate + "' " + "WHERE number = '" + Number + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateOrderPurchasing(String pday, String pquant,String Number){
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query = "UPDATE infi.orders " + "SET purchasing_quantity = '" + pquant + "', purchasing_day ='" + pday + "' WHERE number = '" + Number + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);


        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                orders.add(new Order(rs.getString("name"),
                        rs.getString("number"),
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

    //SELECT * FROM "infi"."MPS" WHERE day = 1;
    public static int[] selectDay(int day){
        numberOfDataBaseQuerys++;
        String query  = "SELECT * FROM \"infi\".\"MPS\" WHERE day = " + day + ";";

        int[] dayData = new int[17];
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                dayData[0] = rs.getInt("day");
                dayData[1] = rs.getInt("comingP1");
                dayData[2] = rs.getInt("comingP2");
                dayData[3] = rs.getInt("productionP3");
                dayData[4] = rs.getInt("productionP4");
                dayData[5] = rs.getInt("productionP5");
                dayData[6] = rs.getInt("productionP6");
                dayData[7] = rs.getInt("productionP7");
                dayData[8] = rs.getInt("productionP8");
                dayData[9] = rs.getInt("productionP9");
                dayData[10] = rs.getInt("deliverP3");
                dayData[11] = rs.getInt("deliverP4");
                dayData[12] = rs.getInt("deliverP5");
                dayData[13] = rs.getInt("deliverP6");
                dayData[14] = rs.getInt("deliverP7");
                dayData[15] = rs.getInt("deliverP8");
                dayData[16] = rs.getInt("deliverP9");
                return dayData;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static int[][] selectAllDays() {
        numberOfDataBaseQuerys++;
        String query = "SELECT * FROM \"infi\".\"MPS\";";

        try (Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Get the number of columns
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create a dynamic list to hold the rows temporarily
            List<int[]> rowsList = new ArrayList<>();

            // Iterate over the result set and store rows in the list
            while (rs.next()) {
                int[] rowData = new int[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = rs.getInt(col + 1);
                }
                rowsList.add(rowData);
            }

            // Check if the rowsList is empty and return null
            if (rowsList.isEmpty()) {
                return null;
            }

            // Convert the list to a two-dimensional array
            int rowCount = rowsList.size();
            int[][] dataRows = new int[rowCount][columnCount];
            for (int i = 0; i < rowCount; i++) {
                dataRows[i] = rowsList.get(i);
            }

            return dataRows;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static List<int[]> selectAllDays2() {
        numberOfDataBaseQuerys++;
        String query = "SELECT * FROM \"infi\".\"MPS\";";

        try (Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<int[]> dataRows = new ArrayList<>();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                int[] rowData = new int[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = rs.getInt(col + 1);
                }
                dataRows.add(rowData);
            }

            return dataRows;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void insertTime(int day, int sec) {
        numberOfDataBaseQuerys++;

        // Connect to the DBMS (DataBase Management Server)
        String deleteQuery = "DELETE FROM infi.time;";
        String insertQuery = "INSERT INTO infi.time (day, sec) VALUES (" + day + ", " + sec + ");";

        try (Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(deleteQuery);
            stmt.executeUpdate(insertQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getDay(){
        numberOfDataBaseQuerys++;
        String query  = "SELECT day FROM infi.time;";
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                return rs.getInt("day");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static int getSec(){
        numberOfDataBaseQuerys++;
        String query  = "SELECT sec FROM infi.time;";
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                return rs.getInt("sec");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static int insertMRP(String number, String wp, int quantity, int day){
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query  = "INSERT INTO \"infi\".\"MRP\" (number, wp, day, quantity) " + "VALUES ('"+ number + "', '" + wp + "', " + day + ", " + quantity + ");";

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

    public static int deleteMRP() {
        // DELETE FROM table_name;
        numberOfDataBaseQuerys++;
        String query = "DELETE FROM \"infi\".\"MRP\";";
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

    public static List<MRP>  selectMRP(){
        numberOfDataBaseQuerys++;
        String query = "SELECT * FROM \"infi\".\"MRP\";";
        List<MRP> mrp = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Analyze the resulting data
            while (rs.next()) {
                String orderNumber = rs.getString("number");
                String workPiece = rs.getString("wp");
                int quantity = rs.getInt("quantity");
                int day = rs.getInt("day");

                MRP mrp1 = new MRP(day, quantity, workPiece, orderNumber);
                mrp.add(mrp1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mrp;
    }

}
