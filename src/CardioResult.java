import java.sql.Time;

/**
 * Created by jorgen on 15/03/16.
 */
public class CardioResult extends Result {

    float lenght;
    Time time;

    public CardioResult(int activityID, int exerciseID, float length, Time time) {
        //super(activityID, exerciseID);
        this.lenght = length;
        this.time = time;
    }
}
