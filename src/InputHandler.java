import java.util.ArrayList;

import result.CardioGoal;
import result.Goal;
import result.StrengthGoal;
import result.CardioResult;
import result.Result;
import result.StrengthResult;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                    case "activitygroup": {
                        SetState(InputHandlerState.ACTIVITYGROUP);
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
                            CreateActivity(input[1], input[2], Integer.parseInt(input[3]), null);
                        } else if (NumArgs(input, 5)) {
                            CreateActivity(input[1], input[2], Integer.parseInt(input[3]), Integer.parseInt(input[4]));
                        } break;
                    case "list":
                        if (NumArgs(input, 1)) {
                            ListActivity();
                        } break;
                    case "results":
                        if (NumArgs(input, 2)) {
                            ListResultsForActivity(input[1]);
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
                    case "list":
                        if (NumArgs(input, 1)) {

                            ListWorkouts();

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

                        if (NumArgs(input, 5)) {
                            CreateStrengthGoal(input[1], input[2], input[3], input[4]);
                        }
                        else if (NumArgs(input, 4) ){
                            CreateCardioGoal(input[1], input[2], input[3]);
                        }
                        System.out.print("Wrong number of arguments");
                        break;
                    case "reach":
                        if (NumArgs(input, 3)){

                            ReachGoal(input [1], input[2]);
                        }
                        System.out.println("Wrong number of arguments");
                        break;
                    case "list":
                        if (NumArgs(input, 2)){
                            ListGoals(input[1]);
                        } break;
                    case "delete":
                        if (NumArgs(input, 3)){
                            DeleteGoal(input[1], input[2]);
                        } break;
                    case "activitygoals":
                        if (NumArgs(input, 2)) {
                            ListGoalsForActivity(input[1]);
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
                            CreateActivityGroup(input[1],Integer.parseInt(input[2]));
                        }
                    } break;
                    case "list": {
                        if (NumArgs(input, 1)) {
                            ListGroupActivities();
                        }
                    } break;
                    case "delete": {
                        if (NumArgs(input, 2)){
                            DeleteActivityGroup(input[1]);
                        }
                    } break;
                    case "back": {
                        SetState(InputHandlerState.MAIN);
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
            //System.out.println("Wrong number of arguments");
            SetState(this.state);
            return false;
        }
    }



    // ----- ACTIVITY ----
    public void CreateActivity(String name, String description, Integer replacement, Integer group ) {
        Activity.add(this.con, name, description, replacement, group);
    }

    public void ListActivity() {

        ArrayList<Activity> dbActivities = Activity.getAll(con);

        for (Activity activity: dbActivities) {
            String ID = Integer.toString(activity.ID);

            System.out.println("Name: " + activity.name);
            System.out.println("    ID: " + ID);
            System.out.println("    Description: " + activity.description);
        }
    }

    public void DeleteActivity(String name) {
        Activity.removeWhereNameLike(this.con, name);
    }

    // ---- ActivityGroup ---

    public void CreateActivityGroup(String name, Integer belongsToGroup){
        ActivityGroup.create(this.con, name, belongsToGroup);
    }

    public void ListGroupActivities(){
        ArrayList<ActivityGroup> groups = ActivityGroup.getAll(this.con);
        for (ActivityGroup ag : groups) {
            System.out.println("Name: " + ag.name);
            System.out.println("    ID: " + Integer.toString(ag.ID));
            System.out.println("    Part of group: " + Integer.toString(ag.belongsToGroup));
        }
    }

    public void DeleteActivityGroup(String name){
        ActivityGroup.removeWhereNameLike(this.con, name);
    }

    // ------ GOAL ------
    public void CreateGoal(String activityID, String type){

    }

    public void CreateStrengthGoal(String activitID, String weight, String set, String reps) {
        try {
            int actID = Integer.parseInt(activitID);
            float w = Float.parseFloat(weight);
            int s = Integer.parseInt(set);
            int r = Integer.parseInt(reps);
            Goal.createStrengthGoal(con, actID, w, s, r);
        }
        catch (NumberFormatException ex) {
            System.out.print("Goal creation not successfull, error in input.");
        }
    }

    public void CreateCardioGoal(String activityID, String length, String duration) {
        try {
            int actID = Integer.parseInt(activityID);
            float l = Float.parseFloat(length);
            float d = Float.parseFloat(duration);
            Goal.createCardioGoal(con, actID, l, d);
        }
        catch (NumberFormatException ex) {
            System.out.print("Goal creation not successfull, error in input.");
        }
    }

    public void ReachGoal(String goalID, String type){
        //Goal.setAsCompleted(this.con, activityID, exerciseID);
        try {
            int g = Integer.parseInt(goalID);

            if (type.toLowerCase().equals("cardio")) {
                Goal.setCardioGoalAsCompleted(con, g);
            }
            else if (type.toLowerCase().equals("strength")) {
                Goal.setStrengthGoalAsCompleted(con, g);
            }
        }
        catch (NumberFormatException ex) {
            System.out.print("ReachGoal failed, wrong argument.");
        }


    }

    public void ListGoals(String type) {

        String t = type.toLowerCase();
        System.out.println("Goals:");

        if (t.equals("all") || t.equals("cardio")) {

            ArrayList<CardioGoal> goals = Goal.getCardioGoals(con);

            for (CardioGoal goal : goals) {
                System.out.println("Type: Cardio" + " | ID: " + goal.getGoalID() + " | ØvelseID: " + goal.getActivityID() + " | Lengde: " + goal.getLenght() + " | Tid: " + goal.getDuration() + " | Fullført: " + goal.getCompleted());
            }

        }
        if (t.equals("all") || t.equals("strength")) {

            ArrayList<StrengthGoal> goals = Goal.getStrengthGoals(con);

            for (StrengthGoal goal: goals) {
                System.out.println("Type: Strength" + " | ID: " + goal.getGoalID() + " | ØvelseID: " + goal.getActivityID() + " | Weight: " + goal.getWeight() + " | Set: " + goal.getSets() + " | Reps: " + goal.getReps() + " | Fullført: " + goal.getCompleted());
            }
        }

    }

    public void ListGoalsForActivity(String activityID) {

        try {
            int actID = Integer.parseInt(activityID);

            ArrayList<Goal> goals = Activity.getGoalsForActivity(con, actID);

            for (Goal goal : goals) {
                if (goal instanceof StrengthGoal) {
                    StrengthGoal sg = (StrengthGoal) goal;
                    System.out.println("Type: Strength" + " | ID: " + sg.getGoalID() + " | ØvelseID: " + sg.getActivityID() + " | Weight: " + sg.getWeight() + " | Set: " + sg.getSets() + " | Reps: " + sg.getReps() + " | Fullført: " + sg.getCompleted());
                }
                else if (goal instanceof CardioGoal) {

                    CardioGoal cg = (CardioGoal) goal;
                    System.out.println("Type: Cardio" + " | ID: " + cg.getGoalID() + " | ØvelseID: " + cg.getActivityID() + " | Lengde: " + cg.getLenght() + " | Tid: " + cg.getDuration() + " | Fullført: " + cg.getCompleted());

                }
            }

        }
        catch (NumberFormatException ex) {
            System.out.println("Input error");
        }

    }

    public void DeleteGoal(String goalID, String type){

        try {
            int g = Integer.parseInt(goalID);

            if (type.toLowerCase().equals("cardio")) {
                Goal.deletCardioGoal(con, g);
            }
            else if (type.toLowerCase().equals("strength")) {
                Goal.deleteStrengthGoal(con, g);
            }
            else {
                System.out.println(type + " is not a valid type. Use cardio/strength");
            }
        }
        catch (NumberFormatException ex) {
            System.out.println("Error in input");
        }

    }

    // ---- RESULT ---

    public void ListResultsForActivity(String activityID) {

        Integer actID = stringToInt(activityID);

        if (actID != null) {
            ArrayList<Result> results = Result.getResultsForActivity(con, actID);

            for (Result result : results) {
                if (result instanceof CardioResult) {
                    CardioResult cr = (CardioResult) result;
                    System.out.println((results.indexOf(result) + 1) + ". Lengde: " + cr.lenght + " | Tid: " + cr.duration);

                }
                else if (result instanceof StrengthResult) {
                    StrengthResult sr = (StrengthResult) result;
                    System.out.println((results.indexOf(result) + 1) + ". Vekt: " + sr.weight + " | Sett: " + sr.sets + " | Reps: " + sr.reps);
                }
            }
        }


    }





    // ---- WORKOUT ----

    public void ListTemplate() {
        Workout.getAllTemplates(this.con);
    }

    public void ListTemplateActivities(String workoutID){
        Workout.getActivitiesFromWorkout(this.con, Integer.parseInt(workoutID));
    }


    public void DeleteTemplate(String workoutID) {
        Workout.deleteWorkoutTemplate(this.con, Integer.parseInt(workoutID));
    }

    public ArrayList<Integer> GetActivitiesFromUser() {

        ArrayList<Integer> inputActivities = new ArrayList<>();
        ArrayList<Activity> dbActivities = Activity.getAll(con);

        System.out.println("Add activities to workout:");

        for (Activity activity: dbActivities) {
            String ID = Integer.toString(activity.ID);
            String name = activity.name;

            System.out.print(name + ": " + ID + ", ");
        }


        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Input activity ID or write done");

            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                int inputID = Integer.parseInt(input);
                inputActivities.add((Integer)inputID);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        }


        return  inputActivities;
    }


    public ArrayList<List> GetResultsFromUser(int workoutID) {

        ArrayList<Integer> activitiesID = Workout.getActivitiesFromWorkout(con, workoutID);
        ArrayList<Activity> dbActivities = Activity.getAll(con);
        System.out.println("Add result:");

        ArrayList<StrengthResult> strengthResults = new ArrayList<>();
        ArrayList<CardioResult> cardioResults = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);


        for (Activity activity: dbActivities) {
            if (activitiesID.contains(activity.ID)) {
                System.out.println("Name: " + activity.name + " ID: " + activity.ID);

                System.out.println("[Weight kg] [Sets] [Reps] for strength activities");
                System.out.println("[Distance km] [Duration sec] for cardio activities");

                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }

                String[] inputList = input.split(" ");

                try {
                    if (inputList.length == 3) {
                        float weight = Float.parseFloat(inputList[0]);
                        int sets = Integer.parseInt(inputList[1]);
                        int reps = Integer.parseInt(inputList[2]);
                        StrengthResult sr = new StrengthResult(activity.ID, workoutID, weight, sets, reps);
                        strengthResults.add(sr);

                    } else if (inputList.length == 2) {
                        float distance = Float.parseFloat(inputList[0]);
                        float duration = Float.parseFloat(inputList[1]);
                        CardioResult cr = new CardioResult(activity.ID, workoutID, distance, duration);
                        cardioResults.add(cr);
                    } else {
                        System.out.println("Invalid input");
                    }

                } catch (Exception ex) {
                    System.out.println("Invalid input");
                }
            }
        }


        ArrayList results = new ArrayList<>();
        results.add(strengthResults);
        results.add(cardioResults);

        return results;
    }



    public void StartWorkout() {
        Scanner scanner = new Scanner(System.in);

        System.out.print(
                "------------------ Create Workout ----------------\n"
        );

        while (true) {


            java.sql.Date date = null;
            Integer shape = null;
            Integer performance = null;
            String notes = null;
            Integer spectators = null;

            String input = null;

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

            while (shape == null) {
                System.out.println("Input shape (0-10): ");
                input = scanner.nextLine();
                try {
                    shape = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input: " + input);
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


            int workoutID = 0;
            while (!input.equalsIgnoreCase("indoor") || !input.equalsIgnoreCase("outdoor")) {

                System.out.println("Indoor or outdoor?");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("indoor")) {
                    String airQuality = null;

                    while (airQuality == null) {
                        System.out.println("Input air quality (Text): ");
                        airQuality = scanner.nextLine();
                    }


                    ArrayList<Integer> activityList = GetActivitiesFromUser();
                    workoutID = Workout.createIndoor(con, activityList, date, shape, performance, notes, spectators, airQuality);
                    break;
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

                    ArrayList<Integer> activityList = GetActivitiesFromUser();
                    workoutID = Workout.createOutdoor(con, activityList, date, shape, performance, notes, spectators, weather, temp);
                    break;
                } else {
                    System.out.println("Invalid input: " + input);
                }
            }


            List results = GetResultsFromUser(workoutID);
            ArrayList<StrengthResult> strengthResults = (ArrayList<StrengthResult>) results.get(0);
            ArrayList<CardioResult> cardioResults = (ArrayList<CardioResult>) results.get(1);

            for (StrengthResult sr: strengthResults) {
                Result.addStrengthResult(this.con, sr.activityID, workoutID, sr.weight, sr.sets, sr.reps);
            }

            for (CardioResult cr: cardioResults) {
                Result.addCardioResult(this.con, cr.activityID, workoutID, cr.lenght, cr.duration);
            }

            System.out.println("All results added!\n");
            return;


        }
    }

    public void StartWorkout(String template){

    }

    public void ListWorkouts() {
        ArrayList<Workout> workouts = Workout.getAll(con);

        for (Workout workout : workouts) {
           ArrayList<Activity> activities = Activity.getActivitiesForWorkout(con,workout.workoutID);

            System.out.println("\nWORKOUT - " + workout.date + "\n" +
            "------------------------------------------------------\n" +
            "ID: " + workout.workoutID + " MalID: " + workout.templateID + " Form: " + workout.shape + "\n" +
            "Prestasjon: " + workout.prestation + " Tilskuere: " + workout.viewers + "\nNotat: " + workout.note + "\n" +
            "------------------------------------------------------");

            for (Activity activity : activities) {
                System.out.println((activities.indexOf(activity) + 1) + ". " + activity.name + " -  Beskrivelse: " + activity.description + " Gruppe: " + activity.groupID + " Erstatning: " + activity.replacement);
                Result result = Result.getResult(con, workout.workoutID, activity.ID);
                if (result instanceof CardioResult) {
                    CardioResult cr = (CardioResult) result;
                    System.out.println("---Resultat: " + "Lengde: " + cr.lenght + " Tid: " + cr.duration);
                }
                else if (result instanceof StrengthResult) {
                    StrengthResult sr = (StrengthResult) result;
                    System.out.println("---Resultat: " + "Vekt: " + sr.weight + " Sett: " + sr.sets + " Reps: " + sr.reps);
                }
            }

        }


    }


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
                    "Back - Return to result menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case WORKOUT:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Workout Commands: \n" +
                    "StartWorkout [Template(Optional)] - Start new workout\n" +
                    "List - List all workouts\n" +
                    "ListActivities [Name] - List all activities in a template\n" +
                    "DeleteTemplate [Name] - Delete a existing workout template\n" +
                    "Back - Return to result menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case GOAL:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Goal Commands: \n" +
                    "Create [ActivityID] [Weight] [Set] [Reps] - Create new goal for a strengthexercise\n" +
                    "Create [ActivityID] [Length(Kilometres)] [Duration(Seconds)] - Create new goal for cardioexercise\n" +
                    "Reach [GoalID] [Cardio/Strength]- Marks the goal as reached\n" +
                    "List [Cardio/Strength/All] - List all goals\n" +
                    "ActivtyGoals [ActivityID] - List all goals for activity\n" +
                    "Delete [GoalID] [Cardio/Strength] - Delete an existing goal\n" +
                    "Back - Return to result menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case ACTIVITYGROUP:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "ActivityGroup Commands: \n" +
                    "Create [Name] (Group)- Create a new group, optionally belonging to another group\n" +
                    "List - List existing ActivityGroups\n" +
                    "Delete [Name] - Delete an existing ActivityGroup\n" +
                    "Back - Return to result menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
        }

        this.state = newState;
    }

    public Integer stringToInt(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            System.out.println("Error in input");
            return null;
        }
    }
}
