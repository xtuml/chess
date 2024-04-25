package components;

import java.util.List;

import org.xtuml.bp.core.ComponentInstance_c;

import lib.pgn.parser.PGNPopulator;
import types.pgn.PGNGame;

public class PGNParser implements IPGNParsingToProvider {

	public PGNParser(IPGNParsingFromProvider peer) {
	}

	@Override
	public List<PGNGame> parse(ComponentInstance_c senderReceiver, String filename) {
		return PGNPopulator.parse(filename);
	}

}
