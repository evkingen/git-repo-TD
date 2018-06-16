import java.sql.*;

/**
 * Created by evkingen on 11.06.2018.
 */
public class SQLHandler {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement pstatement;

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static PreparedStatement getPstatement() {
        return pstatement;
    }

    public static void setPstatement(PreparedStatement pstatement) {
        SQLHandler.pstatement = pstatement;
    }

    public static void SQLСonnect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:jcpl2_db.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void SQLDisconnect(){
        try{
            statement.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void getTable() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS users(\n" + "id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +"    login TEXT UNIQUE,\n" +         "    pass TEXT\n" + ");\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
