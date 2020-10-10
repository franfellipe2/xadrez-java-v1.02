package model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.PiecesObserver;
import model.Board;
import model.enums.Cor;
import model.exceptions.BoardException;
import model.pieces.Bishop;
import model.pieces.Hourse;
import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.PiecePossibleMoves;
import model.pieces.Position;
import model.pieces.Queen;
import model.pieces.Rook;
import values.Strings;

public class Chess {

	Board board;
	protected List<Piece> piecesOnGame = new ArrayList<>();
	protected List<Piece> removedWhitePieces = new ArrayList<>();
	protected List<Piece> removedBlackPieces = new ArrayList<>();
	Cor currentPlayer;
	int turno;
	boolean check, checkMate;
	List<PiecesObserver> piecesObservers = new ArrayList<>();
	Pawn vulnerableEnPassat, promotion;

	public void addPieceObserver(PiecesObserver o) {
		piecesObservers.add(o);
	}

	public boolean hasPromotion() {
		return promotion != null;
	}

	public void promote(String pieceName) {
		if (promotion == null)
			throw new BoardException("Nao existe Peao a ser promovido!");
		String n = pieceName.toUpperCase();
		if (!n.equals("B") && !n.equals("Q") && !n.equals("H") && !n.equals("R"))
			throw new BoardException("Promocao invalida! Escolha B, Q, H ou R.");
		Piece promoted;
		switch (n) {
		case "B":
			promoted = new Bishop(promotion.getCor(), board, this);
			break;
		case "Q":
			promoted = new Queen(promotion.getCor(), board, this);
			break;
		case "H":
			promoted = new Hourse(promotion.getCor(), board, this);
			break;
		default:
			promoted = new Rook(promotion.getCor(), board, this);
		}
		Position p = promotion.getPosition();
		removePiece(p);
		addPiece(p, promoted);
		promotion = null;
		nextTurno();
	}

	public void removePieceObserver(PiecesObserver o) {
		piecesObservers.remove(o);
	}

	public Chess() {
		this.board = new Board();
		currentPlayer = Cor.WHITE;
		initPieces();
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	private void nextTurno() {
		currentPlayer = currentPlayer == Cor.WHITE ? Cor.BLACK : Cor.WHITE;
		turno++;
		check = testeCheck(currentPlayer);
		checkMate = testeCheckMate(currentPlayer);
	}

	public int getTurno() {
		return this.turno;
	}

	public List<Piece> getRemovedBlackPieces() {
		return removedBlackPieces;
	}

	public List<Piece> getRemovedWhitePieces() {
		return removedWhitePieces;
	}

	public Cor getCurrentPlayer() {
		return currentPlayer;
	}

	public Piece[][] getBoardMat() {
		return board.getBoardMat();
	}

	public void addPiece(String coordinate, Piece p) {
		board.addPiece(p, new Position(coordinate));
		piecesOnGame.add(p);
		for (PiecesObserver o : piecesObservers) {
			o.onAdd(p);
		}
	}

	public void addPiece(Position p, Piece piece) {
		board.addPiece(piece, p);
		piecesOnGame.add(piece);
		for (PiecesObserver o : piecesObservers) {
			o.onAdd(piece);
		}
	}

	public Piece removePiece(Position p) {
		Piece piece = board.removePiece(p);
		piecesOnGame.remove(piece);
		for (PiecesObserver o : piecesObservers) {
			o.onRemove(piece);
		}
		return piece;
	}

	public void initPieces() {
		// WHITES
		addPiece("a1", new Rook(Cor.WHITE, board, this));
		addPiece("b1", new Bishop(Cor.WHITE, board, this));
		addPiece("c1", new Hourse(Cor.WHITE, board, this));
		addPiece("d1", new Queen(Cor.WHITE, board, this));
		addPiece("e1", new King(Cor.WHITE, board, this));
		addPiece("f1", new Hourse(Cor.WHITE, board, this));
		addPiece("g1", new Bishop(Cor.WHITE, board, this));
		addPiece("h1", new Rook(Cor.WHITE, board, this));
		addPiece("a2", new Pawn(Cor.WHITE, board, this));
		addPiece("b2", new Pawn(Cor.WHITE, board, this));
		addPiece("c2", new Pawn(Cor.WHITE, board, this));
		addPiece("d4", new Pawn(Cor.WHITE, board, this));
		addPiece("e2", new Pawn(Cor.WHITE, board, this));
		addPiece("f2", new Pawn(Cor.WHITE, board, this));
		addPiece("g2", new Pawn(Cor.WHITE, board, this));
		addPiece("h2", new Pawn(Cor.WHITE, board, this));

		// BLACKS
		addPiece("a8", new Rook(Cor.BLACK, board, this));
		addPiece("b8", new Bishop(Cor.BLACK, board, this));
		addPiece("c8", new Hourse(Cor.BLACK, board, this));
		addPiece("d8", new Queen(Cor.BLACK, board, this));
		addPiece("e8", new King(Cor.BLACK, board, this));
		addPiece("f8", new Hourse(Cor.BLACK, board, this));
		addPiece("g8", new Bishop(Cor.BLACK, board, this));
		addPiece("h8", new Rook(Cor.BLACK, board, this));
		addPiece("a7", new Pawn(Cor.BLACK, board, this));
		addPiece("b7", new Pawn(Cor.BLACK, board, this));
		addPiece("c7", new Pawn(Cor.BLACK, board, this));
		addPiece("d7", new Pawn(Cor.BLACK, board, this));
		addPiece("e7", new Pawn(Cor.BLACK, board, this));
		addPiece("f7", new Pawn(Cor.BLACK, board, this));
		addPiece("g7", new Pawn(Cor.BLACK, board, this));
		addPiece("h7", new Pawn(Cor.BLACK, board, this));
	}

	private boolean testeCheck(Cor player) {
		List<Piece> oponents = piecesOnGame.stream().filter(x -> x.getCor() != player).collect(Collectors.toList());
		Piece King = piecesOnGame.stream().filter(x -> x instanceof King && x.getCor() == player)
				.collect(Collectors.toList()).get(0);

		for (Piece p : oponents) {
			PiecePossibleMoves mv = p.getPossibleMoves();
			if (mv.has(King.getPosition())) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cor player) {
		if (!check)
			return false;
		List<Piece> myPieces = piecesOnGame.stream().filter(x -> x.getCor() == player).collect(Collectors.toList());
		for (Piece p : myPieces) {
			PiecePossibleMoves moves = p.getPossibleMoves();
			Position src = new Position(p.getPosition().getRow(), p.getPosition().getCol());
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					Position target = new Position(i, j);
					if (moves.has(target)) {
						Piece captured = p.move(target);
						boolean isCheck = testeCheck(player);
						p.move(src);
						if (captured != null)
							captured.move(target);

						if (!isCheck)
							return false;
					}
				}
			}

		}
		return true;
	}

	public PerformeChessMoveBuilder createMove() {
		return new PerformeChessMoveBuilder();
	}

	public class PerformeChessMoveBuilder {

		Position source, target;
		Piece piece, capturedPiece;
		PiecePossibleMoves possibleMoves;

		public PiecePossibleMoves source(String coordinate) {
			if (promotion != null)
				throw new BoardException("Promocao pendente para o Peao(" + target + ")!");
			this.source = new Position(coordinate);
			this.piece = board.getPiece(source);
			if (this.piece == null)
				throw new BoardException("Nao existe peca na posicao de origem!");
			if (piece.getCor() != currentPlayer)
				throw new BoardException("Esta peca nao e sua");
			possibleMoves = piece.getPossibleMoves();
			if (!possibleMoves.hasMove())
				throw new BoardException("Esta peca nao pode ser movida!");

			return possibleMoves;
		}

		public void target(String coordinate) {
			this.target = new Position(coordinate);
			if (!board.posExistis(target))
				throw new BoardException("Posicao de destino invalida: " + Strings.noPosition(target));
			if (!possibleMoves.has(target))
				throw new BoardException("Destino invalido!");

		}

		public void build() {
			makeMove();
			if (testeCheck(currentPlayer)) {
				undoMove();
				throw new BoardException("Voce se pos em CHECK!");
			}
			if (promotion == null) {
				nextTurno();
			}
		}

		private void makeMove() {

			capturedPiece = piece.move(target);
			piece.incrementMove();

			vulnerableEnPassat = null;

			if (piece instanceof Pawn) {
				// # Promotion || #EnPassant
				if (target.getRow() == 0 || target.getRow() == 7)
					promotion = (Pawn) piece;
				else if (piece.getMove() == 1 && (target.getRow() == 3 || target.getRow() == 4))
					vulnerableEnPassat = (Pawn) piece;
				// # Roque
			} else if (piece instanceof King) {
				// roque grande
				if (source.getCol() - 2 == target.getCol()) {
					Piece rook = removePiece(new Position(piece.getPosition().getRow(), 0));
					addPiece(new Position(piece.getPosition().getRow(), 3), rook);
					// roque pequeno
				} else if (source.getCol() + 2 == target.getCol()) {
					Piece rook = removePiece(new Position(piece.getPosition().getRow(), 7));
					addPiece(new Position(piece.getPosition().getRow(), 5), rook);
				}
			}

			if (capturedPiece != null) {
				if (capturedPiece.getCor() == Cor.WHITE) {
					removedWhitePieces.add(capturedPiece);
				} else {
					removedBlackPieces.add(capturedPiece);
				}
			}
		}

		private void undoMove() {

			// # Roque
			if (piece instanceof King) {
				// roque grande
				if (source.getCol() - 2 == target.getCol()) {
					Piece rook = removePiece(new Position(piece.getPosition().getRow(), 3));
					addPiece(new Position(piece.getPosition().getRow(), 0), rook);
					// roque pequeno
				} else if (source.getCol() + 2 == target.getCol()) {
					Piece rook = removePiece(new Position(piece.getPosition().getRow(), 5));
					addPiece(new Position(piece.getPosition().getRow(), 7), rook);
				}
			}

			piece.move(source);
			piece.decrementeMove();

			if (capturedPiece != null) {
				addPiece(capturedPiece.getPosition(), capturedPiece);
				if (capturedPiece.getCor() == Cor.WHITE) {
					removedWhitePieces.remove(capturedPiece);
				} else {
					removedBlackPieces.remove(capturedPiece);
				}
			}
		}
	}

	public Pawn getVulnerableEnPassant() {
		return vulnerableEnPassat;
	}

}
