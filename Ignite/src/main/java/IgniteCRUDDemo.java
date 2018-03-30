import java.sql.*;

public class IgniteCRUDDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String jdbcUrl = "jdbc:ignite:thin://172.18.18.105/";
        Connection conn = getIgniteConnection(jdbcUrl);
//        createTabelDemo(conn);
//        createDataForCityTable(conn);
//        createDataForPersonTable(conn);
        getTableDataV1(conn);
//        getTableData(conn);
//        System.out.println("==========================");
//        getTabelDataV2(conn);
//        getTabelDataV3(conn);
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 右连接
    public static void getTabelDataV3(Connection connection) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select p.*, c.* from Person p right join City c on p.city_id = c.id");
            while (rs.next())
                System.out.println(rs.getString(1) + ", " + rs.getString(2)
                        + ", " + rs.getString(3) + ", " + rs.getString(4) + "," + rs.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 左连接
    public static void getTabelDataV2(Connection connection) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select p.*, c.* from Person p left join City c on p.city_id = c.id");
            while (rs.next())
                System.out.println(rs.getString(1) + ", " + rs.getString(2)
                        + ", " + rs.getString(3) + ", " + rs.getString(4) + "," + rs.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 内连接
    public static void getTableDataV1(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.*, c.* FROM Person p inner join City c on p.city_id = c.id");
            while (rs.next())
                System.out.println(rs.getString(1) + ", " + rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 和内连接情况相同
    public static void getTableData(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.name, c.name " +
                            " FROM Person p, City c " +
                            " WHERE p.city_id = c.id");
             while (rs.next())
             System.out.println(rs.getString(1) + ", " + rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createDataForPersonTable(Connection conn) {
        try{
            PreparedStatement stmt =
                    conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)");
//            stmt.setLong(1, 1L);
//            stmt.setString(2, "John Doe");
//            stmt.setLong(3, 3L);
//            stmt.executeUpdate();
//            stmt.setLong(1, 2L);
//            stmt.setString(2, "Jane Roe");
//            stmt.setLong(3, 2L);
//            stmt.executeUpdate();
//            stmt.setLong(1, 3L);
//            stmt.setString(2, "Mary Major");
//            stmt.setLong(3, 1L);
//            stmt.executeUpdate();
//            stmt.setLong(1, 4L);
//            stmt.setString(2, "Richard Miles");
//            stmt.setLong(3, 2L);
//            stmt.executeUpdate();

            PreparedStatement stmtV2 =
                    conn.prepareStatement("INSERT INTO Person (id, name) VALUES (?, ?)");
            stmtV2.setLong(1, 5L);
            stmtV2.setString(2, "Richard Miles");
            stmtV2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDataForCityTable(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)");
//            stmt.setLong(1, 1L);
//            stmt.setString(2, "Forest Hill");
//            stmt.executeUpdate();
//            stmt.setLong(1, 2L);
//            stmt.setString(2, "Denver");
//            stmt.executeUpdate();
//            stmt.setLong(1, 3L);
//            stmt.setString(2, "St. Petersburg");
//            stmt.executeUpdate();
            stmt.setLong(1, 4L);
            stmt.setString(2, "HangZhou");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getIgniteConnection(String jdbcUrl) {
        try {
            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
            try {
                return DriverManager.getConnection(jdbcUrl);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void createTabelDemo(Connection conn){
        try {
            System.out.printf("connection is : " + conn);
            Statement stmt = conn.createStatement();
            // Create database tables
            // Create table based on REPLICATED templatee
            stmt.executeUpdate("CREATE TABLE City (" +
                    " id LONG PRIMARY KEY, name VARCHAR) " +
                    " WITH \"template=replicated\"");
            // Create table based on PARTITIONED template with one backup.
            stmt.executeUpdate("CREATE TABLE Person (" +
                    " id LONG, name VARCHAR, city_id LONG, " +
                    " PRIMARY KEY (id, city_id)) " +
                    " WITH \"backups=1, affinityKey=city_id\"");
            // Create an index on the City table.
            stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");
            // Create an index on the Person table.
            stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

