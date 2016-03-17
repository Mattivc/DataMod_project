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


    public int getActivityID() {
        return activityID;
    }

    public int getGoalID() {
        return goalID;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getCompleted() {
        return completed;
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
            return Goal.getCardioGoalsFromResultSet(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<StrengthGoal> getStrengthGoals(Connection con) {
        try {

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM STYRKEMÅL");

            return Goal.getStrengthGoalsFromResultSet(rs);


        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<CardioGoal> getCardioGoalsFromResultSet(ResultSet rs) {
        try {

            ArrayList<CardioGoal> cardioGoals = new ArrayList();
            while (rs.next()) {

                int goalID = rs.getInt("MålID");
                int actID = rs.getInt("ØvelseID");
                float length = rs.getFloat("Lengde");
                float duration = rs.getFloat("Tid");
                Date date = rs.getDate("Dato");
                boolean completed = rs.getBoolean("Oppnådd");

                CardioGoal goal = new CardioGoal(goalID, actID, length, duration, date, completed);
                cardioGoals.add(goal);
            }

            return cardioGoals;


        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<StrengthGoal> getStrengthGoalsFromResultSet(ResultSet rs) {
        try {

            ArrayList<StrengthGoal> strengthGoals = new ArrayList();
            while (rs.next()) {

                int goalID = rs.getInt("MålID");
                int actID = rs.getInt("ØvelseID");
                float weight = rs.getFloat("Belastning");
                int set = rs.getInt("Antall_sett");
                int reps = rs.getInt("Antall_reps");
                Date date = rs.getDate("Dato");
                boolean completed = rs.getBoolean("Oppnådd");

                strengthGoals.add(new StrengthGoal(goalID, actID, weight, set, reps, date, completed));

            }
            return strengthGoals;


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
