package lib.pgn.parser;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import com.github.bhlangonijr.chesslib.Board;

import components.PGNParser;
import lib.pgn.parser.PGNParser.ElementContext;
import lib.pgn.parser.PGNParser.Element_sequenceContext;
import lib.pgn.parser.PGNParser.Game_terminationContext;
import lib.pgn.parser.PGNParser.Movetext_sectionContext;
import lib.pgn.parser.PGNParser.Pgn_databaseContext;
import lib.pgn.parser.PGNParser.Pgn_gameContext;
import lib.pgn.parser.PGNParser.Recursive_variationContext;
import lib.pgn.parser.PGNParser.San_moveContext;
import lib.pgn.parser.PGNParser.Tag_pairContext;
import lib.pgn.parser.PGNParser.Tag_sectionContext;
import lib.pgn.parser.PGNParser.Tag_valueContext;
import types.pgn.GameResult;
import types.pgn.Line;
import types.pgn.PGNGame;
import types.pgn.Tag;

public class PGNPopulator extends PGNBaseVisitor<Object> {

	private Board board = new Board();

	public static List<PGNGame> parse(String filename) {
		try {

			// Tokenize the file
			final var pgnStream = Optional.ofNullable(PGNPopulator.class.getClassLoader().getResourceAsStream(filename));
			final var input = CharStreams
					.fromStream(pgnStream.orElseThrow(() -> new IOException("File not found: " + filename)));
			final var lexer = new PGNLexer(input);
			final var parser = new lib.pgn.parser.PGNParser(new CommonTokenStream(lexer));
			parser.removeErrorListeners();
			parser.addErrorListener(new BaseErrorListener() {
				@Override
				public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
						int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
					throw new ParseCancellationException(
							filename + ": line " + line + ":" + charPositionInLine + " " + msg);
				}
			});

			// Parse the file
			final var ctx = parser.pgn_database();

			// Walk the parse tree
			final var visitor = new PGNPopulator();
			return visitor.visitPgn_database(ctx);

		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public List<PGNGame> visitPgn_database(Pgn_databaseContext ctx) {
		return ctx.pgn_game().stream().map(this::visitPgn_game).collect(Collectors.toList());
	}

	@Override
	public PGNGame visitPgn_game(Pgn_gameContext ctx) {
		return new PGNGame(visitTag_section(ctx.tag_section()), visitMovetext_section(ctx.movetext_section()),
				ctx.movetext_section().game_termination() != null
						? visitGame_termination(ctx.movetext_section().game_termination())
						: GameResult.ASTERISK);
	}

	@Override
	public List<Line> visitMovetext_section(Movetext_sectionContext ctx) {
		return visitElement_sequence(ctx.element_sequence());
	}

	@Override
	public List<Line> visitElement_sequence(Element_sequenceContext ctx) {
		// loop through elements in reverse order to properly nest the data structure
		final List<Line> lines = new ArrayList<>();
		Line currentElement = null;
		for (final var elementCtx : ctx.element()) {
			final var element = visitElement(elementCtx);
			if (element.isPresent()) {
				final var newElement = element.orElseThrow();
				newElement.setParent(currentElement);
				// if this is the first move, it represents the whole sequence of moves
				if (lines.isEmpty()) {
					lines.add(newElement);
				}
				if (elementCtx.san_move() != null) {
					// if a move, this is the main line and should be the first continuation
					if (currentElement != null) {
						currentElement.getContinuations().add(0, newElement);
					}
					currentElement = newElement;
				} else if (currentElement != null) {
					if (currentElement.getParent() != null) {
						currentElement.getParent().getContinuations().add(newElement);
					} else {
						// this is a line that starts at the first move of the sequence
						lines.add(newElement);
					}
				}
			}
		}
		return lines;
	}

	@Override
	public Optional<Line> visitElement(ElementContext ctx) {
		if (ctx.san_move() != null) {
			return Optional.of(new Line(visitSan_move(ctx.san_move())));
		} else if (ctx.recursive_variation() != null) {
			return Optional.of(visitRecursive_variation(ctx.recursive_variation()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Tag> visitTag_section(Tag_sectionContext ctx) {
		return ctx.tag_pair().stream().map(this::visitTag_pair).collect(Collectors.toList());
	}

	@Override
	public Tag visitTag_pair(Tag_pairContext ctx) {
		return new Tag(ctx.tag_name().getText(), visitTag_value(ctx.tag_value()));
	}

	@Override
	public Line visitRecursive_variation(Recursive_variationContext ctx) {
		final var move = board.undoMove();
		final var oldBoard = board;
		board = oldBoard.clone();
		final var variation = visitElement_sequence(ctx.element_sequence()).get(0); // recursive sequence is always exactly one line
		board = oldBoard;
		board.doMove(move);
		return variation;
	}

	@Override
	public String visitSan_move(San_moveContext ctx) {
		board.doMove(ctx.getText());
		final var move = board.undoMove();
		board.doMove(move);
		return move.toString();
	}

	@Override
	public String visitTag_value(Tag_valueContext ctx) {
		return ctx.getText().substring(1, ctx.getText().length() - 1);
	}

	@Override
	public GameResult visitGame_termination(Game_terminationContext ctx) {
		return GameResult.fromValue(ctx.getText());
	}

	public static void main(String[] args) {
		final var parser = new PGNParser(null);
		final var pgn = parser.parse(null, "openings/white/ScholarsMate.pgn");
		System.out.println("LEVI");
	}

}
