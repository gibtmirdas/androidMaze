package thomas.maze.model.Type;

import android.graphics.RectF;
import thomas.maze.model.utils.FileInterface;

public abstract class Type implements FileInterface {
    protected char symbol;

    protected Type() {
    }

    public char getSymbol() {
        return symbol;
    }
}
