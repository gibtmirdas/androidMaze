package thomas.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    // Le holder
    SurfaceHolder mSurfaceHolder;
    // Le thread dans lequel le dessin se fera DrawingThread mThread;
    DrawingThread mThread;
    Paint mPaint;
    RectF mRect = new RectF(10, 10, 780, 1100);
    RectF mRect2 = new RectF(100, 100, 580, 800);

    public MySurfaceView(Context context) {
        super(context);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mThread = new DrawingThread();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas pCanvas) {
        // Dessinez ici !
        int x = 200;
        int y = 100;
        pCanvas.drawColor(Color.RED);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(30);
        pCanvas.drawRect(mRect, mPaint);
        mPaint.setColor(Color.GREEN);
        pCanvas.drawRect(mRect2, mPaint);
        mPaint.setColor(Color.BLUE);
        pCanvas.drawText("hello !", 400, 300, mPaint);
        for (int i = 0; i < 200; i++) {
            y += 2 * i;
            pCanvas.drawText("hello !", x, y, mPaint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Que faire quand le surface change ? (Si l'utilisateur tourne son téléphone par exemple)
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.keepDrawing = true;
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.keepDrawing = false;
        boolean joined = false;
        while (!joined) {
            try {
                mThread.join();
                joined = true;
            } catch (InterruptedException e) {
            }
        }
    }

    private class DrawingThread extends Thread {
        // pour arreter le dessin quand il le faut
        boolean keepDrawing = true;

        public void run() {
            while (keepDrawing) {
                Canvas canvas = null;
                try {
                    // on chope le canvas pour dessiner dessus
                    canvas = mSurfaceHolder.lockCanvas();
                    // on s'assure qu'aucun autre thread n'y accède
                    synchronized (mSurfaceHolder) {
                        // et on dessine
                        onDraw(canvas);
                    }
                } finally {
                    // le dessin terminé on relâche
// le canvas pour que le dessin s'affiche
                    if (canvas != null)
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
                // pour dessiner a 50 fps;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }

            }
        }

    }
}