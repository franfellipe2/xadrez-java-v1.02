package model.pieces;

public class PiecePossibleMoves {
	boolean[][] moves;
	boolean hasMove = false;

	public PiecePossibleMoves() {
		moves = new boolean[8][8];
	}

	public void add(Position p) {
		hasMove = true;
		moves[p.getRow()][p.getCol()] = true;
	}

	public void add(int row, int col) {
		moves[row][col] = true;
	}

	public boolean hasMove() {
		return hasMove;
	}

	public boolean has(Position p) {
		return moves[p.getRow()][p.getCol()] == true;
	}

	public boolean has(int row, int col) {
		return moves[row][col] == true;
	}

	public boolean[][] getPossibleMoves() {
		return moves;
	}
}
