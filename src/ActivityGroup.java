
import java.sql.*;
import java.util.*;

public class ActivityGroup {


    public int ID;
    public String name;
    public Integer belongsToGroup;

    public ActivityGroup(int ID, String name, Integer belongsToGroup) {
        this.ID = ID;
        this.name = name;
        this.belongsToGroup = belongsToGroup;
    }


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

    public static ArrayList<ActivityGroup> getAll(Connection con){
        try {

            PreparedStatement post = con.prepareStatement("SELECT * FROM GRUPPE");
            ResultSet rs = post.executeQuery();

            ArrayList groups = new ArrayList();
            while (rs.next()) {

                int ID = Integer.parseInt(rs.getString("GruppeID"));
                String name = rs.getString("Navn");
                Integer belongsToGroup = rs.getInt("TilhørerGruppeID");

                ActivityGroup ag = new ActivityGroup(ID, name, belongsToGroup);
                groups.add(ag);
            }

            return groups;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
