package thomas.maze;

import android.content.Context;
import android.graphics.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import thomas.maze.model.Case;
import thomas.maze.model.Type;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder mSurfaceHolder;
    DrawingThread mThread;
    Paint painter;
    private Controller c;
    private float width, height, delta;
    private Bitmap wall, background;

    public MySurfaceView(Context context, Controller c) {
        super(context);
        this.c = c;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mThread = new DrawingThread();
        painter = new Paint();
        painter.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        background = BitmapFactory.decodeResource(getResources(),R.drawable.wood);
        wall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.wall),(int)delta,(int)delta,false);
        Case[][] cases = c.getP().getMaze();
        Case tmp;
        canvas.drawBitmap(background,-50,-50,painter);
        painter.setColor(Color.RED);
        for(int i=0; i<cases.length; i++ ){
            for (int j=0 ; j<cases[i].length; j++){
                tmp = cases[i][j];
                switch (tmp.getType()){
                    case Wall:
                        canvas.drawBitmap(wall, tmp.left,tmp.top,painter);
                        break;
                    case Hole:
                        painter.setColor(Color.BLACK);
                        canvas.drawCircle(tmp.left+delta/2, tmp.top+delta/2, delta/2,painter);
                        break;
                    case Start:
                        painter.setColor(Color.GREEN);
                        canvas.drawCircle(tmp.left+delta/2, tmp.top+delta/2, delta/2,painter);
                        break;
                    case End:
                        painter.setColor(Color.RED);
                        canvas.drawCircle(tmp.left+delta/2, tmp.top+delta/2, delta/2,painter);
                        break;
                    default: // case if empty => do nothing
                        break;
                }
            }
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
        this.width = getWidth();
        this.height = getHeight();
        Case[][] maze = c.getP().getMaze();
        delta = width/ maze[0].length;
        if((height / maze.length) < (width/ maze[0].length))
            delta = height / maze.length;
        for(int i=0; i<maze.length; i++){
            for(int j=0; j<maze[0].length; j++){
                maze[i][j].setPosition(j*delta, i*delta, (j+1)*delta, (i+1)*delta);
            }
        }

        // Init images
        background = BitmapFactory.decodeResource(getResources(),R.drawable.wood);
        wall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.wall),(int)delta,(int)delta,false);
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