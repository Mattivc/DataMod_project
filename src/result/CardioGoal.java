package result;

import java.util.Date;

/**
 * Created by jorgen on 16/03/16.
 */
public class CardioGoal extends Goal {

    float lenght, duration;

    public CardioGoal(int goalID, int activityID, float lenght, float duration) {
        super(goalID, activityID);
        this.lenght = lenght;
        this.duration = duration;
    }

    public CardioGoal(int goalID, int activityID, float lenght, float duration, Date date, boolean completed) {
        super(goalID, activityID, date, completed);
        this.lenght = lenght;
        this.duration = duration;
    }

}
