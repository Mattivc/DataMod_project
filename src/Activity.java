import sun.tools.jconsole.Version;

import java.sql.*;

public class Activity {

    public static boolean add(Connection con, String name, String description, Integer replacement, Integer group) {
        try {
            PreparedStatement post = con.prepareStatement("INSERT INTO ØVELSE (Navn, Beskrivelse, Erstatning, GruppeID) VALUES (?, ?, ?, ?)");
            post.setString(1, name);
            post.setString(2, description);
            if (replacement == null) {
                post.setNull(3, 0);
            } else {
                post.setInt(3, replacement.intValue());
            }

            if (group == null) {
                post.setNull(4, 0);
            } else {
                post.setInt(4, group.intValue());
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
            PreparedStatement post = con.prepareStatement("DELETE FROM ØVELSE WHERE Navn LIKE ?");
            post.setString(1, name);
            post.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

}
