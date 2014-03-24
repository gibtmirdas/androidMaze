package thomas.maze.model.utils;

public interface FileInterface {
	/**
	 * Cases types (array | file):
	 * empty    = 0|-
	 * wall     = 1|#
	 * hole     = 2|o
	 * start    = 3|D
	 * end      = 4|A
	 */
    char EMPTY = '-', WALL = '#', HOLE = 'o', START='D', END='A';
}
