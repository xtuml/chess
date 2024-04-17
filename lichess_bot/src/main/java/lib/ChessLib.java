package lib;

import java.util.List;
import java.util.stream.Collectors;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;

public class ChessLib {
	
	public static List<String> legalMoves(List<String> moves) {
		final var board = new Board();
		if (moves.size() > 0) {
			final var moveList = new MoveList();
			moveList.loadFromSan(moves.stream().collect(Collectors.joining(" ")));
			board.loadFromFen(moveList.getFen());
		}
		return board.legalMoves().stream().map(Move::toString).collect(Collectors.toList());
	}

}