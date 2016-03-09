
import java.sql.*;

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
}
