package model.pieces;

import model.exceptions.BoardException;

public class Position {
	int row, col;
	String chessCoordinate;

	public Position(String chessCoordinate) {
		setChessCoordinate(chessCoordinate);
	}

	public Position(int row, int col) {
		setValues(row, col);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
		setChessCoordinate(row, getCol());
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
		setChessCoordinate(getRow(), col);
	}

	public String getChessCoordinate() {
		return chessCoordinate;
	}

	public void setValues(int row, int col) {
		this.setRow(row);
		this.setCol(col);
		setChessCoordinate(row, col);
	}

	private void setChessCoordinate(int row, int col) {
		this.chessCoordinate = "" + ((char) ('a' + col)) + (8 - row);
	}

	public void setChessCoordinate(String chessCoordinate) {
		if (chessCoordinate.length() < 2 || chessCoordinate.length() > 2)
			throw new BoardException("Coordenadas(" + chessCoordinate + ") invalidas!");
		char[] c = chessCoordinate.toCharArray();
		char col = c[0];
		char row = c[1];
		if (col < 'a' || col > 'h' || row < '1' || row > '8')
			throw new BoardException("Coordenadas(" + chessCoordinate + ") invalidas!");
		this.chessCoordinate = chessCoordinate;
		setValues('8' - row, col - 'a');
	}

	@Override
	public String toString() {
		return getChessCoordinate();
	}

}
