
import result.Result;

import java.sql.*;
import java.util.ArrayList;

public class Activity {

    int ID;
    String name, description;
    Integer replacement, groupID;

    public Activity(int ID, String name, String description, Integer replacement, Integer groupID) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.replacement = replacement;
        this.groupID = groupID;
    }


    public static boolean add(Connection connection, String name, String description, Integer replacement, Integer group) {
        try {
            PreparedStatement post = connection.prepareStatement("INSERT INTO ØVELSE (Navn, Beskrivelse, Erstatning, GruppeID) VALUES (?, ?, ?, ?)");
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

    public static ArrayList<Activity> getAll(Connection con) {
        try {

            PreparedStatement post = con.prepareStatement("SELECT * FROM ØVELSE");
            ResultSet rs = post.executeQuery();

            ArrayList activities = new ArrayList();
            while (rs.next()) {

                int ID = Integer.parseInt(rs.getString("ØvelseID"));
                String name = rs.getString("Navn");
                String description = rs.getString("Beskrivelse");
                Integer replacement = rs.getInt("Erstatning");
                Integer groupID = rs.getInt("GruppeID");

                Activity act = new Activity(ID, name, description, replacement, groupID);
                activities.add(act);
            }

            return activities;


        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Activity> getActivitiesForWorkout(Connection con, int workoutID) {

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM ØVELSE JOIN TRENINGSØKTØVELSE ON TRENINGSØKTØVELSE.ØvelseID LIKE ØVELSE.ØvelseID JOIN TRENINGSØKT ON TRENINGSØKTØVELSE.TreningsØktID LIKE TRENINGSØKT.TreningsØktID WHERE TRENINGSØKT.TreningsØktID LIKE "+workoutID+"");
            ArrayList<Activity> activities = new ArrayList<>();
            while (rs.next()) {

                int activityID = rs.getInt("ØvelseID");
                int replacement = rs.getInt("Erstatning");
                int groupID = rs.getInt("GruppeID");
                String name = rs.getString("Navn");
                String desc = rs.getString("Beskrivelse");

                Activity activity = new Activity(activityID, name, desc, replacement, groupID);
                activities.add(activity);
            }

            return activities;
        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return null;
        }


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
            System.out.print(getActivitiesForWorkout(con, 1));

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

        }

    }



}
