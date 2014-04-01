package thomas.maze;

import android.hardware.Sensor;
import android.util.Log;
import thomas.maze.model.Boule;
import thomas.maze.model.Plateau;
import thomas.maze.model.SensorListener;
import thomas.maze.model.utils.GameState;
import thomas.maze.model.utils.Observable;
import thomas.maze.model.utils.Observer;

/**
 * Created by thomas on 25.03.14 *
 */
public class Controller extends Thread implements Observable{

    private MySurfaceView surfaceView;
    private Plateau p;
    private Boule b;
    private Sensor accelero;
    private float[] accelPos = new float[2];
    private Observer obs;
    public boolean running = true;

    Controller(Plateau p, Sensor accelero) {
        this.p = p;
        this.accelero = accelero;
    }

    @Override
    public void run() {
		// Wait for Plateau to be fully initiate
		while(p.getEndCase() == null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		b = Boule.getInstance();
		b.initiate(p.getWallsNHoles(), p.getEndCase());

		// If cannot acces accelerometer, stop thread
        if (accelero == null)
            return;

		// Start Game Controller !
        while (running) {
            setBoulePos();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }

    private void setBoulePos() {
        accelPos = SensorListener.getVector();
		GameState s = b.setPositionFromTile(accelPos[0], accelPos[1]);
		if(s != GameState.PLAY){
			running = false;
			updateObservateur(s);
		}
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

    @Override
    public void addObservateur(Observer obs) {
        this.obs = obs;
    }

    @Override
    public void updateObservateur(GameState s) {
        if(obs != null)
            obs.update(s);
    }

    @Override
    public void delObservateur() {
        obs = null;
    }
}
