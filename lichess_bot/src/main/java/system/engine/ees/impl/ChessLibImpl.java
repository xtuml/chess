package system.engine.ees.impl;

import java.util.ArrayList;
import java.util.List;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.Utility;
import system.engine.ees.ChessLib;

public class ChessLibImpl<C extends IComponent<C>> extends Utility<C> implements ChessLib {

	public ChessLibImpl(C context) {
		super(context);
	}

	public String sourceFile(final String move) {
		return lib.ChessLib.sourceFile(move);
	}

	public int sourceRank(final String move) {
		return lib.ChessLib.sourceRank(move);
	}

	public String destFile(final String move) {
		return lib.ChessLib.destFile(move);
	}

	public int destRank(final String move) {
		return lib.ChessLib.destRank(move);
	}

	public int legalMoves(final String fen, String[] legalMoves) {
		final List<String> legalMovesList = new ArrayList<>();
		final int legalMoveCount = lib.ChessLib.legalMoves(fen, legalMovesList);
		for (int i = 0; i < legalMovesList.size() && i < legalMoves.length; i++) {
			legalMoves[i] = legalMovesList.get(i);
		}
		return legalMoveCount;
	}

	public String movesToFEN(final String initialFen, final String[] moves) {
		return lib.ChessLib.movesToFEN(initialFen, List.of(moves));
	}

	public String startpos() {
		return lib.ChessLib.startpos();
	}

}
