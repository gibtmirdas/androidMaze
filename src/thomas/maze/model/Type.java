package thomas.maze.model;

import thomas.maze.model.utils.FileInterface;

public enum Type implements FileInterface {
	Wall(WALL), Hole(HOLE), Empty(EMPTY), Start(START), End(END);

	private char symbol;

	Type(char symbol){
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}
}
