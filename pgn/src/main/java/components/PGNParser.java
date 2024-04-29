package components;

import java.util.List;

import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Vm_c;

import lib.pgn.parser.PGNPopulator;
import types.pgn.PGNGame;

public class PGNParser implements IPGNParsingToProvider {

	public PGNParser(IPGNParsingFromProvider peer) {
	}

	@Override
	public List<PGNGame> parse(ComponentInstance_c senderReceiver, String filename) {
		return PGNPopulator.parse(filename, Vm_c.getVmCl(
				PackageableElement_c.getOnePE_PEOnR8001(ComponentReference_c.getOneCL_ICOnR2963(senderReceiver))));
	}

}
