package thomas.maze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
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
        b = Boule.getInstance();
        b.initiate(p.getWallsNHoles(), p.getEndCase());
        if (accelero == null)
            return;
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
        updateObservateur(b.setPositionFromTile(accelPos[0], accelPos[1]));
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
    public void updateObservateur(GameState gs) {
        if(obs != null)
            obs.update(gs);
    }

    @Override
    public void delObservateur() {
        obs = null;
    }
}
