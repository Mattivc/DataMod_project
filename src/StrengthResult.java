/**
 * Created by jorgen on 15/03/16.
 */
public class StrengthResult extends Result {

    double weight;
    int sets, reps;

    public StrengthResult(int activityID, int exerciseID, double weight, int sets, int reps) {
        //super(activityID, exerciseID);
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

}
