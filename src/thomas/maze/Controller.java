package thomas.maze;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import thomas.maze.model.Boule;
import thomas.maze.model.Plateau;

import java.util.Vector;

/** Created by thomas on 25.03.14 **/
public class Controller extends Thread implements SensorEventListener{

    private MySurfaceView surfaceView;
    private Plateau p;
    private Boule b;
	private Sensor accelero;
	private Point accelPos = new Point();
	Controller(Plateau p,Sensor accelero ){
        this.p = p;
		this.accelero = accelero;
		b = Boule.getInstance();
		b.setPosY(p.getMaze()[p.getStart().x][p.getStart().y].left);
    }

    @Override
    public void run() {
        boolean running = true;
		if(accelero == null)
			return;
        while(running){
			setBoulePos();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
        }
    }

	private void setBoulePos(){
		b.setvX(accelPos.x);
		b.setvY(accelPos.y);
	}

	@Override
	public void onSensorChanged(SensorEvent e) {
		if(b== null)
			return;
		accelPos.x = (int) e.values[0]/10;
		accelPos.y = (int) e.values[1]/10;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void setSurfaceView(MySurfaceView surfaceView) {
		this.surfaceView = surfaceView;
	}

	public MySurfaceView getSurfaceView() {
		return surfaceView;
	}

	public Plateau getP() {
		return p;
	}

	public Boule getB() {
		return b;
	}
}
