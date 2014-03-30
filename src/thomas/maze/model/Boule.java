package thomas.maze.model;

import android.graphics.RectF;
import thomas.maze.model.utils.GameState;

import java.util.ArrayList;

/**
 * Created by thomas on 11.03.14.
 */
public class Boule {

    private static Boule b;
    private ArrayList<Case> wallsNHoles;
    private Case endCase;
    private float posx = 0, posy = 0, vx = 0, vy = 0, size = 0, vMax=10;
    //private Color color;
    private RectF rect;
    private long timePrevious = 0;
    private GameState gameState = GameState.PLAY;

    private Boule() {
        rect = new RectF();
    }

    public void initiate(ArrayList<Case> wallsNHoles, Case endCase){
        this.wallsNHoles = wallsNHoles;
        this.endCase = endCase;
    }

    public static Boule getInstance() {
        if (b == null) {
            b = new Boule();
            return b;
        }
        return b;
    }

    public void setMvt(float posX, float posY, float vX, float vY, float vMax) {
        this.posx = posX;
        this.posy = posY;
        this.vx = vX;
        this.vy = vY;
        this.vMax = vMax;

        rect.left = posY;
        rect.top = posX;
        rect.right = posY + size;
        rect.bottom = posX + size;
    }

    public GameState setPositionFromTile(float xAccel, float yAccel) {
        // set delta time with previous frame
        float dt = (System.currentTimeMillis() - timePrevious) / 100f;
        if (timePrevious == 0)
            dt = 0;
        timePrevious = System.currentTimeMillis();
        /****************
         * set velocity *
         ****************/
        float vxOld = vx, vxTmp, vyOld = vy, vyTmp;
        vxTmp = vxOld + xAccel * dt;
        vyTmp = vyOld + yAccel * dt;

        // add friction
        vxTmp *= .95;
        vyTmp *= .95;

        // if vTmp > vMax => set vTmp to vMax
        vx = vxTmp > vMax ? vMax : vxTmp;
        vy = vyTmp > vMax ? vMax : vyTmp;

        /***************************************
         * set position depending on collision *
         ***************************************/
        float xOld = posx, yOld = posy, xTmp, yTmp;
        xTmp = xOld + vx * dt;
        yTmp = yOld + vy * dt;

        // 1st => test if can move horizontally
        posx = xTmp;
        rect.left = posx;
        rect.right = posx + size;
        if(collide2()){
            posx = xOld;
            rect.left = posx;
            rect.right = posx + size;
            vx = 0;
        }
        // 2nd => test if can move vertically
        posy = yTmp;
        rect.top = posy;
        rect.bottom = posy + size;
        if(collide2()){
            posy = yOld;
            rect.top = posy;
            rect.bottom = posy + size;
            vy = 0;
        }

        /**************
         * Game Won ? *
         **************/
        isWon();

        return gameState;
    }

    private void isWon(){
        if(endCase==null)
            return;
        if(RectF.intersects(rect,endCase))
            gameState = GameState.WIN;
    }

    /**
     * Check if boule collide a rectangle in wallsNHoles
     */
    private boolean collide2(){
        for(Case c : wallsNHoles){
            if(RectF.intersects(c,rect)){
                // Case if we hit a Hole => we can approach him by 2/3 of the ball size, otherwise we loose the game
                if(c.getType()== Type.Hole){
                    double distance = Math.sqrt((c.centerX()-rect.centerX())*(c.centerX()-rect.centerX()) + (c.centerY()-rect.centerY())*(c.centerY()-rect.centerY()));
                    if(distance < 2*size/3){
                        gameState = GameState.LOOSE;
                        return true;
                    }
                }else
                    return true;
            }
        }
        return false;
    }

    public float getPosx() {
        return posx;
    }

    public void setPosx(float x0) {
        this.posx = x0;
    }

    public float getPosy() {
        return posy;
    }

    public void setPosy(float y0) {
        this.posy = y0;
    }

    public RectF getRect() {
        return rect;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
    }
}
