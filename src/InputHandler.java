import javafx.scene.layout.Pane;

public class InputHandler {

    public InputHandler() {
        this.SetState(InputHandlerState.MAIN);
    }

    public InputHandlerState state;

    public Boolean HandleInput(String[] input) {

        String cmd = input[0].toLowerCase();

        switch (this.state) {
            case MAIN:
                switch (cmd) {
                    case "activity":
                        SetState(InputHandlerState.ACTIVITY);
                        break;
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;
                }
                break;
            case ACTIVITY:
                switch (cmd) {
                    case "create":
                        if (input.length == 3) {
                            CreateActivity(input[1], input[2]);
                        } else {
                            System.out.println("Wrong number of arguments");
                            SetState(this.state);
                        }
                        break;
                    case "delete":
                        if (input.length == 2) {
                            DeleteActivity(input[1]);
                        } else {
                            System.out.println("Wrong number of arguments");
                            SetState(this.state);
                        }
                        break;
                    case "back":
                        SetState(InputHandlerState.MAIN);
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;
                }
                break;

            case GOAL:

                break;
        }

        return false;
    }

    public void CreateActivity(String name, String description) {

    }

    public void DeleteActivity(String name) {

    }

    public void SetState(InputHandlerState newState) {

        switch (newState){
            case MAIN:
                System.out.print(
                "-----------------------------------------------\n" +
                "Main Commands: \n" +
                "Activity  - Enter Activity menu\n" +
                "-----------------------------------------------\n");
                break;
            case ACTIVITY:
                System.out.print(
                "-----------------------------------------------\n" +
                "Activity Commands: \n" +
                "Create [Name] [Description] - Create a new activity\n" +
                "Delete [Name] - Delete a existing activity\n" +
                "Back - Return to main menu\n" +
                "-----------------------------------------------\n");
                break;
        }

        this.state = newState;
    }
}
