package values;

import model.pieces.Position;

public class Strings {
	public static String noPosition(Position p) {
		return "Esta posicao(" + p + ") nao existe no tabuleiro!";
	}

	public static String alreadyHavePiece(Position p) {
		return "Ja existe peca nesta posicao(" + p + ")!";
	}
}
