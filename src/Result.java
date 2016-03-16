
import java.sql.*;
import java.util.ArrayList;

public class Result {

    int activityID, workoutID;

    public Result(int activityID, int workoutID) {
        this.activityID = activityID;
        this.workoutID = workoutID;
    }


    public static boolean addStrengthResult(Connection connection, int activityID, int workoutID, float weight, int sets, int reps) {

        Result.addResult(connection, activityID, workoutID);

        try {
            PreparedStatement post = connection.prepareStatement("INSERT INTO STYRKE (ØvelseID, TreningsØktID, Belastning, Antall_sett, Antall_reps) VALUES (?, ?, ?, ?, ?)");
            post.setInt(1, activityID);
            post.setInt(2, workoutID);
            post.setFloat(3, weight);
            post.setInt(4, sets);
            post.setInt(5, reps);
            post.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public static boolean addCardioResult(Connection connection, int activityID, int workoutID, float length, float duration) {

        Result.addResult(connection, activityID, workoutID);

        try {
            PreparedStatement post = connection.prepareStatement("INSERT INTO KONDISJON (ØvelseID, TreningsØktID, Lengde, Tid) VALUES (?, ?, ?, ?)");
            post.setInt(1, activityID);
            post.setInt(2, workoutID);
            post.setFloat(3, length);
            post.setFloat(4, duration);
            post.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public static void addResult(Connection connection, int activityID, int workoutID) {
        try {
            PreparedStatement post = connection.prepareStatement("INSERT INTO RESULTAT (ØvelseID, TreningsØktID) VALUES (?, ?)");
            post.setInt(1, activityID);
            post.setInt(2, workoutID);
            post.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList getStrengthResultsForWorkout(Connection connection, int workoutID) {
        try {

            PreparedStatement post = connection.prepareStatement("SELECT * FROM STYRKE WHERE TreningsØktID = ?");
            post.setInt(1, workoutID);
            ResultSet rs = post.executeQuery();

            ArrayList strengthResults = new ArrayList();
            while (rs.next()) {

                int actID = Integer.parseInt(rs.getString("ØvelseID"));
                int woID = Integer.parseInt(rs.getString("TreningsØktID"));
                float weight = rs.getFloat("Belastning");
                int sets = rs.getInt("Antall_sett");
                int reps = rs.getInt("Antall_reps");

                StrengthResult result = new StrengthResult(actID, woID, weight, sets, reps);
                strengthResults.add(result);
            }

            return strengthResults;


        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList getCardioResultsForWorkout(Connection connection, int workoutID) {
        try {

            PreparedStatement post = connection.prepareStatement("SELECT * FROM KONDISJON WHERE TreningsØktID = ?");
            post.setInt(1, workoutID);
            ResultSet rs = post.executeQuery();

            ArrayList cardioResults = new ArrayList();
            while (rs.next()) {

                int actID = Integer.parseInt(rs.getString("ØvelseID"));
                int woID = Integer.parseInt(rs.getString("TreningsØktID"));
                float length = rs.getFloat("Lengde");
                float duration = rs.getFloat("Tid");

                CardioResult result = new CardioResult(actID, woID, length, duration);
                cardioResults.add(result);
            }

            return cardioResults;


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
            //Result.addStrengthResult(con, 1,1, (float)20.0, 3, 12);
            Result.getStrengthResultsForWorkout(con, 1);


        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

        }

    }







}
