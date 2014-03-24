package thomas.maze;

import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.Menu;
import thomas.maze.model.Plateau;

public class MainActivity extends Activity {
    private MySurfaceView viewTest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Plateau p = new Plateau();
        p.readFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/maze1.txt");
        //p.printMaze();

        viewTest = new MySurfaceView(this);
        setContentView(viewTest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
