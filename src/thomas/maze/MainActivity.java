package thomas.maze;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import thomas.maze.model.Plateau;
import thomas.maze.model.SensorListener;
import thomas.maze.model.utils.GameState;
import thomas.maze.model.utils.Observer;
import thomas.maze.model.utils.ShowDialog;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity implements Observer {
	private MySurfaceView viewTest = null;
	Controller c;
	SensorManager sm;
	Sensor accelero;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String model = Build.MODEL;
		Log.d("logcat", "Model: " + model);
		Plateau p = new Plateau();
		InputStream is = null;
		try {
			is = this.getResources().getAssets().open("maze1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.readFile(is);
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelero = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(SensorListener.getInstance(), accelero, SensorManager.SENSOR_DELAY_NORMAL);
		c = new Controller(p, accelero);
		c.addObservateur(this);
		c.start();
		viewTest = new MySurfaceView(this, c);
		c.setSurfaceView(viewTest);
		setContentView(viewTest);
	}


	public void gameWon() {
		viewTest.mThread.keepDrawing = false;
		c.running = false;
		c.interrupt();

		Handler mainHandler = new Handler(this.getMainLooper());
		Runnable myRunnable = new ShowDialog(this, "Win !","Congrat', you beat the game");
		mainHandler.post(myRunnable);
	}

	private void gameOver() {
		viewTest.mThread.keepDrawing = false;
		c.running = false;
		c.interrupt();

		Handler mainHandler = new Handler(this.getMainLooper());
		Runnable myRunnable = new ShowDialog(this, "Game Over","Sorry, but you're lame");
		mainHandler.post(myRunnable);
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
		sm.unregisterListener(SensorListener.getInstance(), accelero);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sm.registerListener(SensorListener.getInstance(), accelero, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void update(GameState s) {
		switch (s) {
			case WIN:
				gameWon();
				break;
			case LOOSE:
				gameOver();
				break;
			default:
				break;
		}
	}
}
