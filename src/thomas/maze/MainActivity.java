package thomas.maze;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import thomas.maze.model.Boule;
import thomas.maze.model.Plateau;
import thomas.maze.model.SensorListener;

public class MainActivity extends Activity {
    private MySurfaceView viewTest = null;
	Controller c;
	SensorManager sm;
	Sensor accelero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Plateau p = new Plateau();
        p.readFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/maze1.txt");
		sm =(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		accelero = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(SensorListener.getInstance(),accelero,SensorManager.SENSOR_DELAY_NORMAL);
        Controller c = new Controller(p,accelero);
        c.start();
        viewTest = new MySurfaceView(this, c);
        c.setSurfaceView(viewTest);
        setContentView(viewTest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	protected void onPause() {
		super.onPause();
		sm.unregisterListener(SensorListener.getInstance(),accelero);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sm.registerListener(SensorListener.getInstance(),accelero,SensorManager.SENSOR_DELAY_NORMAL);
	}
}
