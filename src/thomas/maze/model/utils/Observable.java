package thomas.maze.model.utils;

/**
 * Created by thomas on 31.03.14 *
 */
public interface Observable {
    public void addObservateur(Observer obs);
    public void updateObservateur(GameState gs);
    public void delObservateur();
}
