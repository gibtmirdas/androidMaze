package thomas.maze.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * Created by thomas on 29.03.14 *
 */
public class SensorListener implements SensorEventListener {

    private static float[] vector = {0f,0f};
    private static SensorListener instance;
    private SensorListener() {
    }

    public static SensorListener getInstance(){
        if(instance == null)
            instance = new SensorListener();
        return instance;
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        vector[0] = -e.values[0];
        vector[1] = e.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public static float[] getVector() {
        return vector;
    }
}
