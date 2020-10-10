package interfaces;

import model.pieces.Piece;

public interface PiecesObserver {
	void onRemove(Piece piece);

	void onAdd(Piece piece);
}