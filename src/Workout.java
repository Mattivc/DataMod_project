
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.sql.*;
import java.util.ArrayList;

public class Workout {

    public static boolean createOutdoor(Connection connection, ArrayList<Integer> activities, java.sql.Date date, int shape, int performance, String notes, int specktators, Integer template, String weather, Float temp) {

        int ØktID = Workout.createWorkout(connection, activities, date, shape, performance, notes, specktators, template);

        try {
            PreparedStatement post = connection.prepareStatement("INSERT INTO UTENDØRS (TreningsØktID, Vær, Temp) VALUES (?, ?, ?)");
            post.setInt(1, ØktID);
            post.setString(2, weather);
            post.setFloat(3, temp);
            post.execute();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public static boolean createIndoor(Connection connection, ArrayList activities, java.sql.Date date, int shape, int performance, String notes, int specktators, Integer template, String airQuality) {

        int ØktID = Workout.createWorkout(connection, activities, date, shape, performance, notes, specktators, template);

        try {
            PreparedStatement post = connection.prepareStatement("INSERT INTO INNENDØRS (TreningsØktID, Luftkvalitet) VALUES (?, ?)");
            post.setInt(1, ØktID);
            post.setString(2, airQuality);
            post.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }


    }


    private static Integer createWorkout(Connection connection, ArrayList<Integer> activities, java.sql.Date date, int shape, int performance, String notes, int specktators, Integer template) {

        try {
            PreparedStatement workoutPost = connection.prepareStatement("INSERT INTO TRENINGSØKT (Dato_tid, Form, Prestasjon, Notat, Antall_tilskuere, ØktMalID) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            workoutPost.setDate(1, date);
            workoutPost.setInt(2, shape);
            workoutPost.setInt(3, performance);
            workoutPost.setString(4, notes);
            workoutPost.setInt(5, specktators);

            if (template == null) {
                workoutPost.setNull(6, 0);
            } else {
                workoutPost.setInt(6, template);
            }

            workoutPost.executeUpdate();
            ResultSet rs = workoutPost.getGeneratedKeys();
            rs.next();
            Integer ØktID = rs.getInt(1);

            // Connect activity to workout
            for (Integer item : activities) {
                PreparedStatement activityPost = connection.prepareStatement("INSERT INTO TRENINGSØKTØVELSE (TreningsØktID, ØvelseID) VALUES (?, ?)");
                activityPost.setInt(1, ØktID);
                activityPost.setInt(2, item);
            }

            return ØktID;

        } catch (SQLException ex) {
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
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ArrayList<Integer> activities = new ArrayList<Integer>();
            Workout.createOutdoor(con, activities, sqlDate, 4, 4, "Notes", 0, null, "Pretty good", (float)22.1);
            Workout.createIndoor(con, activities, sqlDate, 2, 6, "Notes", 0, null, "Good");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

        }

    }





}
