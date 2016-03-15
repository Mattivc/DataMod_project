import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
                String input = scanner.nextLine();

                Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
                List<String> inputCommands = new LinkedList<>();

                while (m.find()) {
                    inputCommands.add(m.group(1).replace("\"", ""));
                }

                for (String s: inputCommands) {
                    System.out.print(s + "|");
                }
                System.out.print("\n");

                assert inputCommands.size() >= 1: "Invalid command";

                if (inputHandler.HandleInput(inputCommands.toArray(new String[inputCommands.size()]))) { break; }
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
