
import java.util.*;

/**
 * Created by kirichek on 11/20/16.
 */

public class TripParameters {
    public static final int MAX_SPEED = 100;
    public static final int MIN_SPEED = 0;
    public static final int MAX_ANGLE = 180;
    public static final int MIN_ANGLE = -180;

    private int speed;
    private int angle;

    private static TripParameters instance = null;
    private List<ParametersChangeListener> listeners = new ArrayList<>();

    private TripParameters(){}

    public void setAngle(int angle) {
        this.angle = angle;
        notifyListeners();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        notifyListeners();
    }

    public int getAngle() {
        return angle;
    }

    public int getSpeed() {
        return speed;
    }

    public static TripParameters getInstance() {
        if(instance == null){
            instance = new TripParameters();
        }
        return instance;
    }

    public void addListener(ParametersChangeListener toAdd) {
        listeners.add(toAdd);
    }


    private void notifyListeners() {
        for (ParametersChangeListener hl : listeners)
            hl.parametersChanged(speed, angle);
    }


}
