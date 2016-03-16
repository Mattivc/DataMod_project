package result;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class Goal {


    int activityID, exerciseID;
    Date date;
    Boolean completed;



    public Goal(int activityID, int exerciseID) {
        this.activityID = activityID;
        this.exerciseID = exerciseID;
        this.date = null;
        this.completed = false;
    }

    public Goal(int activityID, int exerciseID, Date date, Boolean completed) {

        this.activityID = activityID;
        this.exerciseID = exerciseID;
        this.date = date;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return this.activityID + " " + this.exerciseID + " " + this.date + " " + this.completed;
    }


    private static Goal create(Connection con, int activityID, int workoutID) {

        Result.addResult(con, activityID, workoutID);

        try {

            PreparedStatement st = con.prepareStatement("INSERT INTO MÅL (ØvelseID, TreningsØktID, Oppnådd) VALUES (?,?,?)");
            st.setInt(1, activityID);
            st.setInt(2, workoutID);
            st.setBoolean(3, false);
            st.execute();

        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return null;
        }


        return new Goal(activityID, workoutID);


    }

    public static boolean createStrengthResult(Connection connection, int activityID, int workoutID, float weight, int sets, int reps) {


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

    public static Boolean delete(Connection con, int activityID, int worokoutID) {

        try {
            con.createStatement().execute("DELETE FROM MÅL WHERE ØvelseID LIKE "+activityID+" AND MÅL.TreningsØktID LIKE "+worokoutID+"");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Goal> getAll(Connection con) {

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM MÅL");
            ArrayList<Goal> list = new ArrayList<>();
            while (rs.next()){
                list.add(new Goal(rs.getInt("ØvelseID"), rs.getInt("TreningsØktID"), rs.getDate("Dato"),
                        rs.getBoolean("Oppnådd")));
            }
            return list;

        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Goal get(Connection con, int activityID, int workoutID) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM MÅL WHERE ØvelseID LIKE "+activityID+" AND MÅL.TreningsØktID LIKE "+workoutID+"");
            if (rs.next()) {
                return new Goal(rs.getInt("ØvelseID"), rs.getInt("TreningsØktID"),rs.getDate("Dato"),  rs.getBoolean("Oppnådd"));
            }
            else {
                return null;
            }
        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Boolean setAsCompleted(Connection con, int activityID, int workoutID) {
        try {
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement st = con.prepareStatement("UPDATE MÅL SET Oppnådd=?, Dato=? WHERE ØvelseID LIKE "+activityID+" AND TreningsØktID LIKE "+workoutID+"");
            st.setBoolean(1, true);
            st.setDate(2, sqlDate);
            st.execute();
            return true;
        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Boolean setAsCompleted(Connection con, Goal goal) {
        return setAsCompleted(con, goal.activityID, goal.exerciseID);
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
            Goal.delete(con, 1, 1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try{
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }







    }


}
