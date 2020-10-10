package model.pieces;

import model.Board;
import model.enums.Cor;
import model.game.Chess;

public class King extends Piece {

	public King(Cor cor, Board board, Chess chess) {
		super(cor, board, chess);
		this.name = 'K';
	}

	@Override
	public PiecePossibleMoves getPossibleMoves() {
		PiecePossibleMoves moves = new PiecePossibleMoves();

		addMove(moves, -1, 0);// N
		addMove(moves, -1, 1);// NE
		addMove(moves, 0, 1);// E
		addMove(moves, 1, 1);// SE
		addMove(moves, 1, 0);// S
		addMove(moves, 1, -1);// SW
		addMove(moves, 0, -1);// W
		addMove(moves, -1, -1);// NW

		// Roque Grande
		Piece piece = board.getPiece(new Position(position.getRow(), 0));
		if (this.moves == 0 && piece != null && piece instanceof Rook && piece.moves == 0 /**/
				&& board.getPiece(new Position(position.getRow(), 1)) == null /**/
				&& board.getPiece(new Position(position.getRow(), 2)) == null /**/
				&& board.getPiece(new Position(position.getRow(), 3)) == null /**/
		)
			moves.add(new Position(position.getRow(), 2));

		// Roque pequeno
		piece =  board.getPiece(new Position(position.getRow(), 7));
		if (this.moves == 0 && piece != null && piece instanceof Rook && piece.moves == 0 /**/
				&& board.getPiece(new Position(position.getRow(), 5)) == null /**/
				&& board.getPiece(new Position(position.getRow(), 6)) == null /**/
		)
			moves.add(new Position(position.getRow(), 6));

		return moves;

	}

	private void addMove(PiecePossibleMoves moves, int row, int col) {
		Position p = new Position(position.getRow() + row, position.getCol() + col);
		if (canMove(p) || getOponent(p) != null)
			moves.add(p);
	}

}
