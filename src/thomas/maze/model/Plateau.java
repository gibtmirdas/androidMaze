package thomas.maze.model;

import android.util.Log;
import thomas.maze.model.Type.*;
import thomas.maze.model.utils.FileInterface;

import java.io.*;

public class Plateau implements FileInterface {

    /**
     * Cases types (array | file):
     * empty    = 0|-
     * wall     = 1|#
     * hole     = 2|o
     * start    = 3|D
     * end      = 4|A
     */
    private Cases[][] maze;

    public Plateau() {
    }

    /**
     * Create a maze by reading a text file
     * @param fileName
     */
    public void readFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            int width, height;
            height = Integer.parseInt(br.readLine());
            width = Integer.parseInt(br.readLine());
            maze = new Cases[height][width];
            String line;
            Cases c;
            for (int i = 0; i < height; i++) {
                line = br.readLine();
                for (int j = 0; j < width; j++) {
                    try {
                        c = new Cases(convertFileCharToType(line.charAt(j)), i, j);
                        maze[i][j] = c;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("logcat","Character not recognized : "+i+"-"+j);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if(!valideMaze()){
            Log.d("logcat", "Maze not valide");
            maze = new Cases[0][0];
        }
    }

    /**
     * Convert text file's char to a type
     * @param c
     * @return
     * @throws Exception
     */
    private Type convertFileCharToType(char c) throws Exception{
        switch (c){
            case FileInterface.EMPTY:
                return new Empty();
            case FileInterface.WALL:
                return new Wall();
            case FileInterface.HOLE:
                return new Trou();
            case FileInterface.START:
                return new Depart();
            case FileInterface.END:
                return new Arrivee();
        }
        throw new Exception("logcat : Character not recognized for maze creating");
    }

    public void printMaze(){
        for (int i=0 ; i< maze.length ; i++){
            String val = "";
            for (int j=0 ; j< maze[i].length ; j++){
                val += maze[i][j].getType().getClass();
            }
            Log.d("logcat", val);
        }
    }

    private boolean valideMaze(){
        boolean hasStart= false, hasEnd=false;
        for(int i=0; i<maze.length; i++){
            for(int j=0; j<maze[i].length; j++){
                //Check start
                if(maze[i][j].getType().getClass() == Depart.class)
                    hasStart = true;
                // Check End
                if(maze[i][j].getType().getClass() == Arrivee.class)
                    hasEnd = true;
                // Check Border
                if(i == 0 || j == 0 || i == (maze.length-1) || j== (maze[i].length-1))
                    if(maze[i][j].getType().getClass() != Wall.class){
                        Log.d("logcat", "Not bordered");
                        return false;
                    }
            }
        }
        return hasEnd && hasStart;
    }
}
