package lib;

import java.util.List;
import java.util.stream.Collectors;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;

public class ChessLib {

	public static final String STARTPOS = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

	public static List<String> legalMoves(String fen) {
		final var board = new Board();
		board.loadFromFen(fen);
		return board.legalMoves().stream().map(Move::toString).collect(Collectors.toList());
	}

	public static String sourceFile(String move) {
		if (move.matches("[a-h][1-8][a-h][1-8]")) {
			return "" + move.charAt(0);
		} else {
			throw new IllegalArgumentException("Not a valid chess move: " + move);
		}
	}

	public static int sourceRank(String move) {
		if (move.matches("[a-h][1-8][a-h][1-8]")) {
			return move.charAt(1) - '0';
		} else {
			throw new IllegalArgumentException("Not a valid chess move: " + move);
		}
	}

	public static String destFile(String move) {
		if (move.matches("[a-h][1-8][a-h][1-8]")) {
			return "" + move.charAt(2);
		} else {
			throw new IllegalArgumentException("Not a valid chess move: " + move);
		}
	}

	public static int destRank(String move) {
		if (move.matches("[a-h][1-8][a-h][1-8]")) {
			return move.charAt(3) - '0';
		} else {
			throw new IllegalArgumentException("Not a valid chess move: " + move);
		}
	}

	public static String movesToFEN(List<String> moves) {
		if (moves.size() > 0) {
			final var moveList = new MoveList();
			moveList.loadFromSan(moves.stream().collect(Collectors.joining(" ")));
			return moveList.getFen();
		}
		return STARTPOS;
	}

}