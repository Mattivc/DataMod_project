package result;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public abstract class Goal {

    int activityID, goalID;
    Date date;
    Boolean completed;


    public Goal(int goalID, int activityID) {
        this.activityID = activityID;
        this.goalID = goalID;
        this.date = null;
        this.completed = false;
    }


    public Goal(int goalID, int activityID, Date date, Boolean completed) {
        this.activityID = activityID;
        this.goalID = goalID;
        this.date = date;
        this.completed = completed;
    }




    public static boolean createStrengthGoal(Connection con, int activityID, float weight, int sets, int reps) {

        try {

            PreparedStatement st = con.prepareStatement("INSERT INTO STYRKEMÅL(ØvelseID, Belastning, Antall_sett, Antall_reps) VALUES (?,?,?,?)");
            st.setInt(1, activityID);
            st.setFloat(2, weight);
            st.setInt(3, sets);
            st.setInt(4, reps);
            st.execute();

            return true;
        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }


    public static boolean createCardioGoal(Connection con, int activityID, float length, float duration) {

        try {
            PreparedStatement st = con.prepareStatement("INSERT INTO KONDISJONMÅL(ØvelseID, Lengde, Tid) VALUES (?,?,?)");
            st.setInt(1, activityID);
            st.setFloat(2, length);
            st.setFloat(3, duration);
            st.execute();
            return true;
        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public static boolean setCardioGoalAsCompleted(Connection con, int goalID) {

        try {
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement st = con.prepareStatement("UPDATE KONDISJONMÅL SET Oppnådd=?, Dato=? WHERE KONDISJONMÅL.MålID LIKE "+goalID+"");
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

    public static boolean setStrengthGoalAsCompleted(Connection con, int goalID) {
        try {
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement st = con.prepareStatement("UPDATE STYRKEMÅL SET Oppnådd=?, Dato=? WHERE STYRKEMÅL.MålID LIKE "+goalID+"");
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


    public static ArrayList<CardioGoal> getCardioGoals(Connection con) {
        try {

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM KONDISJONMÅL");

            ArrayList cardioGoals = new ArrayList();
            while (rs.next()) {

                int goalID = rs.getInt("MålID");
                int actID = rs.getInt("ØvelseID");
                float length = rs.getFloat("Lengde");
                float duration = rs.getFloat("Tid");
                Date date = rs.getDate("Dato");
                boolean completed = rs.getBoolean("Oppnådd");

                CardioGoal goal = new CardioGoal(goalID, actID, )
                cardioResults.add(result);
            }

            return cardioResults;


        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }




    public static boolean deletCardioGoal(Connection con, int goalID) {
        try {
            con.createStatement().execute("DELETE FROM KONDISJONMÅL WHERE MålID LIKE "+goalID+"");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean deleteStrengthGoal(Connection con, int goalID) {
        try {
            con.createStatement().execute("DELETE FROM STYRKEMÅL WHERE MålID LIKE "+goalID+"");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    /*public static ArrayList<Goal> getAll(Connection con) {

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

*/
    public static void main(String[] args){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/Trening?useSSL=false";
        String user = "user";
        String password = "12345678";

        try {
            con = DriverManager.getConnection(url, user, password);
            Goal.deleteStrengthGoal(con, 1);

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
