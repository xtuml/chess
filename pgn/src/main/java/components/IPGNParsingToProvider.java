package components;

import java.util.List;

import org.xtuml.bp.core.ComponentInstance_c;

import types.pgn.PGNGame;

public interface IPGNParsingToProvider {

	List<PGNGame> parse(ComponentInstance_c senderReceiver, String filename);

}
