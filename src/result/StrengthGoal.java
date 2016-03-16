package result;

import java.util.Date;

/**
 * Created by jorgen on 16/03/16.
 */
public class StrengthGoal extends Goal {

    float weight;
    int sets, reps;

    public StrengthGoal(int goalID, int activityID, float weight, int sets, int reps) {
        super(goalID, activityID);
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

    public StrengthGoal(int goalID, int activityID, float weight, int sets, int reps, Date date, boolean completed) {
        super(goalID, activityID, date, completed);
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

}
