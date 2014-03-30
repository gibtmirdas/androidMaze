package thomas.maze.model;

import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    // Get direction for collision
    private boolean verticalPositive, horizontalPositive;

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

    public void setPositionFromTile(float xAccel, float yAccel) {
        // set delta time
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

        // Set direction booleans
        horizontalPositive = vxTmp >= 0 ? true:false;
        verticalPositive = vxTmp >= 0 ? true:false;

        // add friction
        vxTmp *= .95;
        vyTmp *= .95;
        // TODO test if velocity < vMax
        vx = vxTmp;
        vy = vyTmp;

        /****************
         * set position *
         ****************/
        float xOld = posx, yOld = posy, xTmp, yTmp;
        xTmp = xOld + vx * dt;
        yTmp = yOld + vy * dt;

        posx = xTmp;
        posy = yTmp;

        /*****************
         * set rectangle *
         *****************/
        rect.left = posx;
        rect.top = posy;
        rect.right = posx + size;
        rect.bottom = posy + size;

        // TODO test if collision
        int col = collide();
        if(col != 0){
            if(col == 2 || col == 3){
                vy = 0;
                this.posy = yOld;
            }
            if(col == 1 || col == 3){
                vx = 0;
                this.posx = xOld;
            }
        }
    }

    /**
     * Check if boule collide a rectangle in wallsNHoles
     * @return 0: no collision - 1: collide on x axis - 2: collide on y axis - 3: both
     */
    private int collide(){
        int ret = 0;
        Float[] deltas = new Float[]{Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE};
        for(Case c : wallsNHoles){
            if(RectF.intersects(c,rect)) {
                if(rect.right > c.left && rect.left < c.left){             // Ball on left
                    Log.d("logcat","left");
                    deltas[0] = rect.right - c.left;
                }if(rect.left < c.right && rect.right > c.right){         // Ball on Right
                    Log.d("logcat","Right");
                    deltas[1] = c.right - rect.left;
                }if(rect.top < c.bottom && rect.bottom > c.bottom){             // Ball on Bottom
                    Log.d("logcat","Bottom");
                    deltas[2] = c.bottom - rect.top;
                }if(rect.bottom > c.top && rect.top < c.top){             // Ball on Top
                    Log.d("logcat","Top");
                    deltas[3] = rect.bottom - c.top;
                }

                // Get minimums delta to identify where the intersection is exacly
                List<Float> list = Arrays.asList(deltas);
                int indexMin = list.indexOf(Collections.min(list));
                // Left and Right
                if(indexMin < 2){
                    if(ret == 2) ret = 3;
                    else if(ret != 3) ret = 1;
                }else{
                    if(ret == 1) ret = 3;
                    else if(ret != 3) ret = 2;
                }
            }
        }
        return ret;
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
