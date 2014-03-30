package thomas.maze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import thomas.maze.model.Boule;
import thomas.maze.model.Plateau;
import thomas.maze.model.SensorListener;
import thomas.maze.model.utils.GameState;
import thomas.maze.model.utils.Observer;

public class MainActivity extends Activity implements Observer{
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
        c = new Controller(p,accelero);
        c.addObservateur(this);
        c.start();
        viewTest = new MySurfaceView(this, c);
        c.setSurfaceView(viewTest);
        setContentView(viewTest);
    }


    public void gameWon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Win !");
        builder.setMessage("You won the game\n");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dial = builder.create();
        dial.setCancelable(false);
        dial.show();
    }

    private void gameOver() {
        try {
            viewTest.mThread.keepDrawing = false;
            c.running = false;
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Looser !");
        builder.setMessage("You loose the game :(\n");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dial = builder.create();
        dial.setCancelable(false);
        dial.show();
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

    @Override
    public void update(GameState gameState) {
        switch (gameState) {
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
