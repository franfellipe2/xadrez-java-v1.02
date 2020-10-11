package control;

import java.util.Scanner;

import interfaces.PiecesObserver;
import model.exceptions.BoardException;
import model.game.Chess;
import model.game.Chess.PerformeChessMoveBuilder;
import model.pieces.Piece;
import model.pieces.PiecePossibleMoves;
import model.pieces.Rook;
import view.ChessView;

public class main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Chess chess = new Chess();
		PerformeChessMoveBuilder move = chess.createMove();
		
		ChessView view = new ChessView(chess);

		while (!chess.isCheckMate()) {
			try {
				view.draw();
				System.out.print("Origem: ");
				String source = sc.next();
				PiecePossibleMoves possibleMOves = move.source(source);
				view.draw(possibleMOves);
				System.out.print("Destino: ");
				String target = sc.next();
				move.target(target);
				move.build();
				if (chess.hasPromotion()) {
					System.out.print("Escolha uma promocao(B/Q/H/R): ");
					String promotion = sc.next();
					chess.promote(promotion);
				}
			} catch (BoardException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
				sc.nextLine();
			}
		}

		view.draw();

	}

}
