
import java.sql.*;
import java.util.*;

public class ActivityGroup {

    public static boolean create(Connection con, String name, Integer belongsToGroup) {
        try {
            PreparedStatement post = con.prepareStatement("INSERT INTO GRUPPE (Navn, TilhørerGruppeID) VALUES (?, ?)");
            post.setString(1, name);
            if (belongsToGroup == null) {
                post.setNull(2, 0);
            } else {
                post.setInt(2, belongsToGroup.intValue());
            }

            post.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean removeWhereNameLike(Connection con, String name) {
        try {
            PreparedStatement post = con.prepareStatement("DELETE FROM GRUPPE WHERE Navn LIKE ?");
            post.setString(1, name);
            post.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public static ArrayList<Activity> getAll(Connection con, String name){
        ArrayList<Activity> list = new ArrayList<>();
        //TODO
        return list;
    }



    public static void main(String[] args){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/Trening?useSSL=false";
        String user = "user";
        String password = "12345678";

        try {
            con = DriverManager.getConnection(url, user, password);
            ActivityGroup.create(con, "Benk", null);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

        }







    }



}
