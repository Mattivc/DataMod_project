package result;

public class CardioResult extends Result {

    public float lenght, duration;

    public CardioResult(int activityID, int workoutID, float length, float duration) {
        super(activityID, workoutID);
        this.lenght = length;
        this.duration = duration;
    }
}
