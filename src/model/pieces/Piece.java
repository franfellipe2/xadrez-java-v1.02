package model.pieces;

import model.Board;
import model.enums.Cor;
import model.game.Chess;

public abstract class Piece {
	Cor cor;
	char name;
	int moves;
	Position position;
	Chess chess;
	Board board;

	public Piece(Cor cor, Board board, Chess chess) {
		super();
		this.cor = cor;
		this.chess = chess;
		this.board = board;
	}

	public void incrementMove() {
		this.moves++;
	}

	public void decrementeMove() {
		this.moves--;
	}

	public Piece getOponent(Position p) {
		if (!board.posExistis(p))
			return null;
		Piece o = board.getPiece(p);
		return o != null && o.getCor() != getCor() ? o : null;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "" + name;
	}

	public boolean canMove(Position p) {
		return board.posExistis(p) && board.getPiece(p) == null;
	}

	public abstract PiecePossibleMoves getPossibleMoves();

	public Piece move(Position target) {
		Piece captured = chess.removePiece(target);
		chess.removePiece(this.position);
		chess.addPiece(target.getChessCoordinate(), this);
		setPosition(target);
		return captured;
	}

	public int getMove() {
		return this.moves;
	}

}
