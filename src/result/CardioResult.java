package result;

public class CardioResult extends Result {

    float lenght, duration;

    public CardioResult(int activityID, int exerciseID, float length, float duration) {
        super(activityID, exerciseID);
        this.lenght = length;
        this.duration = duration;
    }
}
