package model;

import model.exceptions.BoardException;
import model.pieces.Piece;
import model.pieces.Position;
import values.Strings;

public class Board {
	Piece[][] pieces = new Piece[8][8];

	public Piece[][] getBoardMat() {
		return pieces;
	}

	public void addPiece(Piece piece, Position p) {
		if (!posExistis(p))
			throw new BoardException(Strings.noPosition(p));
		if (hasPiece(p))
			throw new BoardException(Strings.alreadyHavePiece(p));
		pieces[p.getRow()][p.getCol()] = piece;
		piece.setPosition(p);
	}

	public Piece removePiece(Position p) {
		if (!posExistis(p))
			throw new BoardException(Strings.noPosition(p));
		Piece piece = pieces[p.getRow()][p.getCol()];
		pieces[p.getRow()][p.getCol()] = null;
		return piece;
	}

	public Piece getPiece(Position p) {
		return posExistis(p) ? pieces[p.getRow()][p.getCol()] : null;
	}

	// ===== VERIFY ====
	public boolean posExistis(Position p) {
		return p.getRow() >= 0 && p.getRow() < 8 && p.getCol() >= 0 && p.getCol() < 8;
	}

	public boolean hasPiece(Position p) {
		return getPiece(p) != null;
	}
}
