package model.pieces;

import model.Board;
import model.enums.Cor;
import model.game.Chess;

public class Hourse extends Piece {

	public Hourse(Cor cor, Board board, Chess chess) {
		super(cor, board, chess);
		this.name = 'H';
	}

	@Override
	public PiecePossibleMoves getPossibleMoves() {
		PiecePossibleMoves moves = new PiecePossibleMoves();

		addMove(moves, -1, 2);// N
		addMove(moves, -1, -2);// NE
		addMove(moves, 1, 2);// E
		addMove(moves, 1, -2);// SE
		addMove(moves, 2, 1);// S
		addMove(moves, -2, 1);// SW
		addMove(moves, 2, -1);// W
		addMove(moves, -2, -1);// NW
		
		return moves;

	}

	private void addMove(PiecePossibleMoves moves, int row, int col) {
		Position p = new Position(position.getRow() + row, position.getCol() + col);
		if (canMove(p) || getOponent(p) != null)
			moves.add(p);
	}

}
