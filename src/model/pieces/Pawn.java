package model.pieces;

import model.Board;
import model.enums.Cor;
import model.game.Chess;

public class Pawn extends Piece {

	public Pawn(Cor cor, Board board, Chess chess) {
		super(cor, board, chess);
		this.name = 'P';
	}

	@Override
	public PiecePossibleMoves getPossibleMoves() {

		PiecePossibleMoves moves = new PiecePossibleMoves();
		if (getCor() == Cor.WHITE) {

			addMove(moves, -1, 0);
			addMove(moves, -1, -1);
			addMove(moves, -1, +1);

			// # En Passant: left
			Position p = new Position(position.getRow(), position.getCol() - 1);
			Piece oponent = getOponent(p);
			if (oponent != null && oponent instanceof Pawn && oponent.moves == 1 && oponent.position.getRow() == 3) {
				p.setValues(p.getRow() - 1, p.getCol());
				moves.add(p);
			}
			// # En Passant: right
			p = new Position(position.getRow(), position.getCol() + 1);
			oponent = getOponent(p);
			if (oponent != null && oponent instanceof Pawn && oponent.moves == 1 && oponent.position.getRow() == 3) {
				p.setValues(p.getRow() - 1, p.getCol());
				moves.add(p);
			}
		} else {
			addMove(moves, 1, 0);
			addMove(moves, 1, -1);
			addMove(moves, 1, +1);

			// # En Passant: left
			Position p = new Position(position.getRow(), position.getCol() + 1);
			Piece oponent = getOponent(p);
			if (oponent != null && oponent instanceof Pawn && oponent.moves == 1 && oponent.position.getRow() == 3) {
				p.setValues(p.getRow() + 1, p.getCol());
				moves.add(p);
			}
			// # En Passant: right
			p = new Position(position.getRow(), position.getCol() + 1);
			oponent = getOponent(p);
			if (oponent != null && oponent instanceof Pawn && oponent.moves == 1 && oponent.position.getRow() == 3) {
				p.setValues(p.getRow() + 1, p.getCol());
				moves.add(p);
			}
		}

		return moves;

	}

	private void addMove(PiecePossibleMoves moves, int row, int col) {
		Position p = new Position(0, 0);
		p.setValues(position.getRow() + row, position.getCol() + col);
		if (canMove(p) && col == 0) {
			moves.add(p);
			p.setValues(p.row + row, p.getCol());
			if (this.moves == 0 && canMove(p))
				moves.add(p);
		} else if (col != 0 && getOponent(p) != null) {
			moves.add(p);
		}
	}

	@Override
	public Piece move(Position target) {
		Piece captured;
		Piece capturedEnPassant = null;
		// # En Passant
		if (this.position.getCol() != target.getCol()) {
			Position p = new Position(target.getRow() + (getCor() == Cor.WHITE ? 1 : -1), target.getCol());
			Piece oponent = getOponent(p);
			if (oponent != null && oponent == chess.getVulnerableEnPassant()) {
				capturedEnPassant = chess.removePiece(p);
			}
		}
		captured = super.move(target);
		return capturedEnPassant != null ? capturedEnPassant : captured;

	}

}
