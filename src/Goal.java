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


    public static Boolean create(Connection con, int activityID, int exerciseID) {


        try {

            PreparedStatement st = con.prepareStatement("INSERT INTO MÅL VALUES (?,?,?)");
            st.setInt(1, activityID);
            st.setInt(2, exerciseID);
            st.setBoolean(3, false);
            st.execute();

        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;

    }

    public static ArrayList<Goal> getAll(Connection con) {

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM MÅL");
            ArrayList<Goal> list = new ArrayList<>();
            int index = 0;
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

    public static Boolean setAsCompleted(Connection con, int activityID, int exerciseID) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE MÅL SET Oppnådd = true WHERE ØvelseID LIKE "+activityID+" AND TreningsØktID LIKE "+exerciseID+"");
            return true;
        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return false;
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
            st = con.createStatement();
            System.out.print(Goal.setAsCompleted(con, 1, 2));

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
