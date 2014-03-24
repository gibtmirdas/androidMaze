package thomas.maze.model;

import android.graphics.Color;
import android.graphics.RectF;

/**
 * Created by thomas on 11.03.14.
 */
public class Boule {

    private float posX, posY, vX, vY, vMax, size;
    private Color color;
    private RectF rect;

    public Boule() {
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getvX() {
        return vX;
    }

    public void setvX(float vX) {
        this.vX = vX;
    }

    public float getvY() {
        return vY;
    }

    public void setvY(float vY) {
        this.vY = vY;
    }

    public float getvMax() {
        return vMax;
    }

    public void setvMax(float vMax) {
        this.vMax = vMax;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }
}
