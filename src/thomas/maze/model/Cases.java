package thomas.maze.model;

import android.graphics.RectF;
import thomas.maze.model.Type.Type;

/**
 * Created by thomas on 11.03.14.
 */
public class Cases {
    private Type type;
    private RectF rect;
    private int x, y;

    public Cases(Type type, float left, float top, float right, float bottom){
        this.type = type;
        rect = new RectF(left,top,right,bottom);
    }

    public Cases(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Update case position (left, top, right, bottom) depending on its position (x,y) and the maze size and display size
     * @param winHeight
     * @param winWidth
     * @param mazeHeight
     * @param mazeWidth
     */
    public void setPosition(int winHeight, int winWidth, int mazeHeight, int mazeWidth){
        // To be sure case remains a square
        int squareSize=0;
        if((winHeight/mazeHeight) > (winWidth/mazeWidth))
            squareSize = winWidth/mazeWidth;
        else
            squareSize = winHeight / mazeHeight;

        // update position
        rect.left = squareSize*y;
        rect.top = squareSize*x;
        rect.right = squareSize*(y+1);
        rect.bottom = squareSize*(x+1);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
