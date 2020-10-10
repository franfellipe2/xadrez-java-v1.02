package model.pieces;

import model.Board;
import model.enums.Cor;
import model.game.Chess;

public class Queen extends Piece {

	public Queen(Cor cor, Board board, Chess chess) {
		super(cor, board, chess);
		this.name = 'Q';
	}

	@Override
	public PiecePossibleMoves getPossibleMoves() {

		PiecePossibleMoves moves = new PiecePossibleMoves();

		addMove(moves, -1, -1);// N
		addMove(moves, -1, 1);// E
		addMove(moves, 1, -1);// S
		addMove(moves, 1, 1);// W

		addMove(moves, 0, 1);// N
		addMove(moves, 1, 0);// E
		addMove(moves, 0, -1);// S
		addMove(moves, -1, 0);// W
		
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

	@Override
	public Piece move(Position target) {
		// TODO Auto-generated method stub
		return super.move(target);
	}

}
