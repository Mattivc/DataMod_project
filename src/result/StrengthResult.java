package result;

/**
 * Created by jorgen on 15/03/16.
 */
public class StrengthResult extends Result {

    float weight;
    int sets, reps;

    public StrengthResult(int activityID, int workoutID, float weight, int sets, int reps) {
        super(activityID, workoutID);
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

}
