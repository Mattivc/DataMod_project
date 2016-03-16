package result;

/**
 * Created by jorgen on 16/03/16.
 */
public class CardioGoal extends Goal {

    float lenght, duration;

    public CardioGoal(int activityID, int exerciseID, float lenght, float duration) {
        super(activityID, exerciseID);
        this.lenght = lenght;
        this.duration = duration;
    }

}
