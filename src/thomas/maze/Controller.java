package thomas.maze;

import thomas.maze.model.Boule;
import thomas.maze.model.Plateau;

/** Created by thomas on 25.03.14 **/
public class Controller extends Thread{

    private MySurfaceView surfaceView;
    private Plateau p;
    private Boule b;
    Controller(Plateau p){
        this.p = p;
    }

    @Override
    public void run() {
        boolean running = true;
        while(running){

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
}
