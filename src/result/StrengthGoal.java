package result;

/**
 * Created by jorgen on 16/03/16.
 */
public class StrengthGoal extends Goal {

    float weight;
    int sets, reps;

    public StrengthGoal(int activityID, int workoutID, float weight, int sets, int reps) {
        super(activityID, workoutID);
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

}
