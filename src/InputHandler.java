import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputHandler {

    Connection con;

    public InputHandler(Connection con) {
        this.SetState(InputHandlerState.MAIN);
        this.con = con;
    }

    public InputHandlerState state;

    public Boolean HandleInput(String[] input) {

        String cmd = input[0].toLowerCase();

        switch (this.state) {
            case MAIN:
                switch (cmd) {
                    case "activity": {
                        SetState(InputHandlerState.ACTIVITY);
                    } break;
                    case "goal": {
                        SetState(InputHandlerState.GOAL);
                    } break;
                    case "workout": {
                        SetState(InputHandlerState.WORKOUT);
                    } break;
                    case "quit": {
                        System.out.println("Goodbye.");
                        return true;
                    }
                    default: {
                        System.out.println("Invalid command: " + cmd);
                    } break;
                }
                break;
            case ACTIVITY:
                switch (cmd) {
                    case "create":
                        if (NumArgs(input, 3)) {
                            CreateActivity(input[1], input[2], null, null);
                        } else if (NumArgs(input, 4)) {
                            CreateActivity(input[1], input[2], input[3], null);
                        } else if (NumArgs(input, 5)) {
                            CreateActivity(input[1], input[2], input[3], input[4]);
                        break;
                    }
                    case "list":
                        if (NumArgs(input, 1)) {
                            ListActivity();
                        } break;
                    case "delete":
                        if (NumArgs(input, 2)) {
                            DeleteActivity(input[1]);
                        } break;
                    case "back":
                        if (NumArgs(input, 1)) {
                            SetState(InputHandlerState.MAIN);
                        } break;
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;
                }
                break;

            case WORKOUT:
                switch (cmd) {
                    case "createtemplate":
                        if (NumArgs(input, 2)) {

                        } break;
                    case "listtemplates":
                        if (NumArgs(input, 1)) {

                        } break;
                    case "listactivities":
                        if (NumArgs(input, 1)) {
                            ListTemplateActivities(input[1]);
                        }
                    case "deletetemplate":
                        if (NumArgs(input, 2)) {

                        } break;
                    case "startworkout":
                        if (input.length == 1) {
                            StartWorkout();
                        } else if (input.length == 2) {
                            StartWorkout(input[1]);
                        } else {
                            System.out.println("Wrong number of arguments");
                            SetState(this.state);
                        }
                    case "back":
                        if (NumArgs(input, 1)) {
                            SetState(InputHandlerState.MAIN);
                        } break;
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;
                }
                break;
            case GOAL:
                switch (cmd){
                    case "create":
                        if (NumArgs(input, 2)){
                            CreateGoal(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        } break;
                    case "reach":
                        if (NumArgs(input, 3)){
                            ReachGoal(Integer.parseInt(input[1]), Integer.parseInt(input[2]), input[3].equalsIgnoreCase("yes"));
                        } break;
                    case "list":
                        if (NumArgs(input, 1)){
                            ListGoals();
                        } break;
                    case "delete":
                        if (NumArgs(input, 2)){
                            DeleteGoal(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        } break;
                    case "back":
                        if (NumArgs(input, 1)) {
                            SetState(InputHandlerState.MAIN);
                        } break;
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;

                }
                break;
            case ACTIVITYGROUP:
                switch (cmd) {
                    case "create": {
                        if (NumArgs(input, 2)){
                            CreateActivityGroup(input[1], null);
                        } else if (NumArgs(input, 3)){
                            CreateActivityGroup(input[1],input[2]);
                        }
                    } break;
                    case "list ": {
                        if (NumArgs(input, 1)) {
                            ListGroupActivities(input[1]);
                        }
                    } break;
                    case "delete": {
                        if (NumArgs(input, 1)){
                            DeleteActivityGroup(input[1]);
                        }
                    } break;
                    case "back": {
                    }
                    default: {
                        System.out.println("Invalid command: " + cmd);
                    } break;
                }
                break;

        }

        return false;
    }

    public boolean NumArgs(String[] input, int n) {
        if (input.length == n) {
            return true;
        } else {
            System.out.println("Wrong number of arguments");
            SetState(this.state);
            return false;
        }
    }



    // ----- ACTIVITY ----
    public void CreateActivity(String name, String description, String replacement, String group ) {
        Activity.add(this.con, name, description, Integer.parseInt(replacement), Integer.parseInt(group));
    }

    public void ListActivity() {
        //Activity.getAll(this.con);
    }

    public void DeleteActivity(String name) {
        Activity.removeWhereNameLike(this.con, name);
    }

    // ---- ActivityGroup ---

    public void CreateActivityGroup(String name, String belongsToGroup){
        ActivityGroup.create(this.con, name, Integer.parseInt(belongsToGroup));
    }

    public void ListGroupActivities(String name){
        ActivityGroup.getAll(this.con, name);
    }

    public void DeleteActivityGroup(String name){
        ActivityGroup.removeWhereNameLike(this.con, name);
    }

    // ------ GOAL ------
    public void CreateGoal(int activityID, int exerciseID){
        Goal.create(con, activityID, exerciseID);

    }

    public void ReachGoal(int activityID, int exerciseID, boolean reached){
        if (reached) {
            Goal.setAsCompleted(this.con, activityID, exerciseID);
        }
    }

    public void ListGoals() {
        Goal.getAll(this.con);
    }

    public void DeleteGoal(int activityID, int exerciseID){
        //Goal.delete(this.con, activityID, exerciseID);
    }
    

    // ---- WORKOUT ----

    public void ListTemplate() {
        //TODO

    }

    public void ListTemplateActivities(String workoutID){
        Workout.getActivitiesFromWorkout(this.con, Integer.parseInt(workoutID));
    }


    public void DeleteTemplate(String name) {
        //TODO
    }

    public void StartWorkout() {
        Scanner scanner = new Scanner(System.in);

        System.out.print(
                "------------------ Create Workout ----------------\n"
        );

        while (true) {


            java.sql.Date date = null;
            Integer performance = null;
            String notes = null;
            Integer spectators = null;

            String input;

            while (date == null) {
                System.out.println("Date in format(DD.MM.YYYY-HH:MM) or (now)");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("now")) {
                    java.util.Date nowDate = new Date();
                    date = new java.sql.Date(nowDate.getTime());
                } else {
                    try {
                        DateFormat format = new SimpleDateFormat("d.M.Y-H:m");
                        java.util.Date inputDate = format.parse(input);
                        date = new java.sql.Date(inputDate.getTime());
                    } catch (ParseException ex) {
                        System.out.println("Invalid input");
                    }
                }
            }

            while (performance == null) {
                System.out.println("Input performance (0-10): ");
                input = scanner.nextLine();
                try {
                    performance = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input: " + input);
                }
            }


            while (notes == null) {
                System.out.println("Input notes (Text): ");
                notes = scanner.nextLine();
            }

            while (spectators == null) {
                System.out.println("Input spectators (Integer): ");
                input = scanner.nextLine();
                try {
                    spectators = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input: " + input);
                }
            }

            System.out.println("Indoor or outdoor?");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("indoor")) {
                String airQuality = null;

                while (airQuality == null) {
                    System.out.println("Input air quality (Text): ");
                    airQuality = scanner.nextLine();
                }

                // CALL WORKOUT HERE
                return;
            } else if(input.equalsIgnoreCase("outdoor")) {

                String weather = null;
                Float temp = null;

                while (weather == null) {
                    System.out.println("Input weather (Text): ");
                    weather = scanner.nextLine();
                }

                while (temp == null) {
                    System.out.println("Input temperature (Decimal number): ");
                    input = scanner.nextLine();
                    try {
                        temp = Float.parseFloat(input);
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid input: " + input);
                    }

                }

                // CALL WORKOUT HERE
                return;
            } else {
                System.out.println("Invalid input: " + input);
            }
        }
    }

    public void StartWorkout(String template) {}


    public void SetState(InputHandlerState newState) {

        switch (newState){
            case MAIN:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Main Commands: \n" +
                    "Activity  - Enter Activity menu\n" +
                    "ActivityGroup - Enter ActivityGroup menu\n" +
                    "Goal - Enter Goal menu\n" +
                    "Workout - Enter Workout menu\n" +
                    "Quit - Exit program\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case ACTIVITY:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Activity Commands: \n" +
                    "Create [Name] [Description] (Replacement) (Group)- Create a new activity\n" +
                    "List - List all activities\n" +
                    "Delete [Name] - Delete a existing activity\n" +
                    "Back - Return to main menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case WORKOUT:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Workout Commands: \n" +
                    "StartWorkout [Template(Optional)] - Start new workout\n" +
                    "ListTemplates - List all workouts\n" +
                    "ListActivities [Name] - List all activities in a template\n" +
                    "DeleteTemplate [Name] - Delete a existing workout template\n" +
                    "Back - Return to main menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case GOAL:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Goal Commands: \n" +
                    "Create [ActivityID] [ExerciseID]- Create a new goal for the given activity in exercise\n" +
                    "Reach [ActivityID] [ExerciseID] [YES/NO]- Say if the goal was reached or not\n" +
                    "List - List all goals\n" +
                    "Delete [ActivityID] [ExerciseID] - Delete an existing goal for the given activity in exercise\n" +
                    "Back - Return to main menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case ACTIVITYGROUP:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "ActivityGroup Commands: \n" +
                    "Create [Name] (Group)- Create a new group, optionally belonging to another group\n" +
                    "List [name] - List existing ActivityGroups\n" +
                    "Delete [Name] - Delete an existing ActivityGroup\n" +
                    "Back - Return to main menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
        }

        this.state = newState;
    }
}
