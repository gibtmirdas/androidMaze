package thomas.maze.model;

import android.graphics.RectF;

/**
 * Created by thomas on 11.03.14.
 */
public class Case extends RectF {
    private Type type;
    private int x, y;

    public Case(Type type, float left, float top, float right, float bottom) {
        this.type = type;

    }

    public Case(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Update case position (left, top, right, bottom)
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setPosition(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public boolean intersect(RectF r) {
        RectF tmp = r;
        return super.intersect(tmp);
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            if (this != null) {
                Case c = new Case(type, x, y);
                c.setPosition(left, top, right, bottom);
                return c;
            }
            return null;
        }
        return o;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
