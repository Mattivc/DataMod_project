import sun.tools.jconsole.Version;

import java.sql.*;

public class Activity {

    public static void add(Connection con, String name, String description, Integer replacement, Integer group) {
        try {
            PreparedStatement post = con.prepareStatement("INSERT INTO ØVELSE (Navn, Beskrivelse, Erstatning, GruppeID) VALUES (?, ?, ?, ?)");
            post.setString(1, name);
            post.setString(2, description);
            post.setNull(3, 0);
            post.setNull(4, 0);
            post.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void remove(Statement st) {

    }

    public static void edit(Statement st) {

    }



    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/Trening?useSSL=false";
        String user = "user";
        String password = "12345678";

        try {
            con = DriverManager.getConnection(url, user, password);
            add(con, "Benkd", "Press", null, null);

        } catch (SQLException var16) {
            var16.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }

                if(st != null) {
                    st.close();
                }

                if(con != null) {
                    con.close();
                }
            } catch (SQLException var15) {
                var15.printStackTrace();
            }

        }

    }



}
