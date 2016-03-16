package result;

/**
 * Created by jorgen on 16/03/16.
 */
public class CardioGoal extends Goal {

    float lenght, duration;

    public CardioGoal(int activityID, int workoutID, float lenght, float duration) {
        super(activityID, workoutID);
        this.lenght = lenght;
        this.duration = duration;
    }

}
