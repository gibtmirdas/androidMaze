package thomas.maze.model;

import android.graphics.Point;
import android.util.Log;
import thomas.maze.model.utils.FileInterface;

import java.io.*;

public class Plateau implements FileInterface {

    private Case[][] maze;
	private Point start, end;

    public Plateau() {
    }

    /**
     * Create a maze by reading a text file
     * @param fileName
     */
    public void readFile(String fileName) {
		maze = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            int width, height;
            height = Integer.parseInt(br.readLine());
            width = Integer.parseInt(br.readLine());
            maze = new Case[height][width];
            String line;
            Case c;
            for (int i = 0; i < height; i++) {
                line = br.readLine();
                for (int j = 0; j < width; j++) {
                    try {
                        c = new Case(convertFileCharToType(line.charAt(j)), i, j);
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
            maze = new Case[0][0];
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
                return Type.Empty;
            case FileInterface.WALL:
                return Type.Wall;
            case FileInterface.HOLE:
                return Type.Hole;
            case FileInterface.START:
                return Type.Start;
            case FileInterface.END:
                return Type.End;
        }
        throw new Exception("logcat : Character not recognized for maze creating");
    }

    public void printMaze(){
        for (int i=0 ; i< maze.length ; i++){
            String val = "";
            for (int j=0 ; j< maze[i].length ; j++){
                val += maze[i][j].getType();
            }
            Log.d("logcat", val);
        }
    }

    private boolean valideMaze(){
        boolean hasStart= false, hasEnd=false;
        for(int i=0; i<maze.length; i++){
            for(int j=0; j<maze[i].length; j++){
                //Check start
                if(maze[i][j].getType() == Type.Start){
                    hasStart = true;
					start = new Point(i,j);
				}
                // Check End
                if(maze[i][j].getType() == Type.End){
                    hasEnd = true;
					end = new Point(i,j);
                // Check Border
				}if(i == 0 || j == 0 || i == (maze.length-1) || j== (maze[i].length-1))
                    if(maze[i][j].getType() != Type.Wall){
                        Log.d("logcat", "Not bordered");
                        return false;
                    }
            }
        }
        return hasEnd && hasStart;
    }

    public Case[][] getMaze() {
        return maze;
    }

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}
}
