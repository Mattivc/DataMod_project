import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;


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


    public static Goal create(Connection con, int activityID, int exerciseID) {


        try {

            PreparedStatement st = con.prepareStatement("INSERT INTO MÅL (ØvelseID, TreningsØktID, Oppnådd) VALUES (?,?,?)");
            st.setInt(1, activityID);
            st.setInt(2, exerciseID);
            st.setBoolean(3, false);
            st.execute();

        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return null;
        }


        return new Goal(activityID, exerciseID);


    }

    public static Boolean delete(Connection con, int activityID, int exerciseID) {

        try {
            con.createStatement().execute("DELETE FROM MÅL WHERE ØvelseID LIKE "+activityID+" AND MÅL.TreningsØktID LIKE "+exerciseID+"");
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

    public static Goal get(Connection con, int activityID, int exerciseID) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM MÅL WHERE ØvelseID LIKE "+activityID+" AND MÅL.TreningsØktID LIKE "+exerciseID+"");
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

    public static Boolean setAsCompleted(Connection con, int activityID, int exerciseID) {
        try {
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement st = con.prepareStatement("UPDATE MÅL SET Oppnådd=?, Dato=? WHERE ØvelseID LIKE "+activityID+" AND TreningsØktID LIKE "+exerciseID+"");
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
