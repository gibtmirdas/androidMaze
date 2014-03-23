package thomas.maze.graphMotor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by thomas on 11.03.14.
 */
public class Painter {
    private Bitmap b;
    private Canvas c;
    private Paint p;

    public Painter() {
        b = Bitmap.createBitmap(128,128, Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
        p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

}
