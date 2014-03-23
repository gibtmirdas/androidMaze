package thomas.maze.model;

import android.graphics.RectF;

/**
 * Created by thomas on 11.03.14.
 */
public class Cases extends RectF{
    private Type type;
    private int x, y;

    public Cases(Type type, float left, float top, float right, float bottom){
        super(left, top, right, bottom);
        this.type = type;
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
        this.left = squareSize*y;
        this.top = squareSize*x;
        this.right = squareSize*(y+1);
        this.bottom = squareSize*(x+1);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
