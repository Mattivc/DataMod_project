import java.sql.*;
import java.util.Scanner;


public class TreningProgram {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/Trening?useSSL=false";
        String user = "user";
        String password = "12345678";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();

            Scanner scanner = new Scanner(System.in);
            InputHandler inputHandler = new InputHandler();

            while (true) {
                System.out.print("cmd: ");
                String[] input = scanner.nextLine().split(" +");

                assert input.length >= 1: "Invalid command";

                if (inputHandler.HandleInput(input)) { break; }
            }

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
