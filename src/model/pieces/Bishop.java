package model.pieces;

import model.Board;
import model.enums.Cor;
import model.game.Chess;

public class Bishop extends Piece {

	public Bishop(Cor cor, Board board, Chess chess) {
		super(cor, board, chess);
		this.name = 'B';
	}

	@Override
	public PiecePossibleMoves getPossibleMoves() {
		
		PiecePossibleMoves moves = new PiecePossibleMoves();

		addMove(moves, -1, -1);// N
		addMove(moves, -1, 1);// E
		addMove(moves, 1, -1);// S
		addMove(moves, 1, 1);// W

		return moves;

	}

	private void addMove(PiecePossibleMoves moves, int row, int col) {
		Position p = new Position(position.getRow() + row, position.getCol() + col);
		while (canMove(p)) {
			moves.add(p);
			p.setValues(p.getRow() + row, p.getCol() + col);
		}
		if (getOponent(p) != null)
			moves.add(p);
	}

}
