package view;

import java.util.Arrays;

import model.enums.Cor;
import model.game.Chess;
import model.pieces.Piece;
import model.pieces.PiecePossibleMoves;

public class ChessView {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	Chess chess;
	PiecePossibleMoves possibleMoves;
	int corCell = 1;// 0 para branco 1 para preto

	public ChessView(Chess chess) {
		this.chess = chess;
	}

	public void draw() {
		makeDraw();
	}

	public void draw(PiecePossibleMoves possibleMoves) {
		this.possibleMoves = possibleMoves;
		makeDraw();
		this.possibleMoves = null;
	}

	private void makeDraw() {
		clearScreen();
		Piece[][] mat = chess.getBoardMat();
		System.out.println("   ------- XADREZ -------");
		if (chess.getRemovedWhitePieces().size() > 0)
			System.out.println(Arrays.toString(chess.getRemovedWhitePieces().toArray()));
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < 8; j++) {
				Piece p = mat[i][j];
				corCell = corCell == 1 ? 0 : 1;
				if (p == null) {
					System.out.print(printCell(" ", possibleMoves != null && possibleMoves.has(i, j)));
				} else {
					String item = p.getCor() == Cor.WHITE ? (corCell == 1 ? ANSI_BLACK + p : "" + p) : ANSI_PURPLE + p;
					System.out.print(printCell(item, possibleMoves != null && possibleMoves.has(i, j)));
				}
			}
			corCell = corCell == 1 ? 0 : 1;
			System.out.println();
		}
		System.out.println("   a  b  c  d  e  f  g  h");

		if (chess.getRemovedBlackPieces().size() > 0)
			System.out.println(ANSI_PURPLE + Arrays.toString(chess.getRemovedBlackPieces().toArray()) + ANSI_RESET);
		if (chess.isCheckMate()) {
			System.out.println(ANSI_GREEN + "CHECK-MATE!" + ANSI_RESET);
		} else if (chess.isCheck()) {
			System.out.println(ANSI_RED + "CHECK!" + ANSI_RESET);
		}

		System.out.println();
		System.out.println("Turno " + chess.getTurno());
		if (!chess.isCheckMate()) {
			System.out.println("Aguardando jogador: " + chess.getCurrentPlayer());
		} else{
			System.out.println("Vencedor " + (chess.getCurrentPlayer() == Cor.WHITE ? Cor.BLACK : Cor.WHITE));
		}
	}

	private String printCell(String item, Boolean hasMove) {
		if (hasMove) {
			if (corCell == 1) {
				return ANSI_GREEN_BACKGROUND + " " + item + " " + ANSI_RESET;
			} else {
				return ANSI_BLUE_BACKGROUND + " " + item + " " + ANSI_RESET;
			}
		} else {
			if (corCell == 1) {
				return ANSI_WHITE_BACKGROUND + " " + item + " " + ANSI_RESET;
			} else {
				return " " + item + " "+ ANSI_RESET;
			}
		}

	}

	/**
	 * {@link} https://stackoverflow.com/questions/2979383/java-clear-the-
	 * console
	 */
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

}
