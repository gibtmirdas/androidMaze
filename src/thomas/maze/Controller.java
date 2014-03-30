package thomas.maze;

import android.hardware.Sensor;
import thomas.maze.model.Boule;
import thomas.maze.model.Plateau;
import thomas.maze.model.SensorListener;

/** Created by thomas on 25.03.14 **/
public class Controller extends Thread {

    private MySurfaceView surfaceView;
    private Plateau p;
    private Boule b;
	private Sensor accelero;
	private float[] accelPos = new float[2];

	Controller(Plateau p,Sensor accelero ){
        this.p = p;
		this.accelero = accelero;
    }

    @Override
    public void run() {
        b = Boule.getInstance();
        b.initiate(p.getWallsNHoles(), p.getEndCase());
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
        accelPos = SensorListener.getVector();
        b.setPositionFromTile(accelPos[0],accelPos[1]);
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
