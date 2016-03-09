import javax.xml.transform.Result;
import java.sql.*;
import java.util.Date;
import java.util.Calendar;


public abstract class Goal {

    public static Boolean create(Connection con, int activityID, int exerciseID, java.util.Date date) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        try {

            PreparedStatement st = con.prepareStatement("INSERT INTO MÅL VALUES (?,?,?,?)");
            st.setInt(1, activityID);
            st.setInt(2, exerciseID);
            st.setDate(3, sqlDate);
            st.setBoolean(4, false);
            st.execute();

        }
        catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;

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
            Goal.create(con,1,2,new Date(200, 3, 23));

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
