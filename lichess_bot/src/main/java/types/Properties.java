package types;

import io.ciera.runtime.summit.exceptions.NotImplementedException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;

public class Properties extends java.util.Properties implements IXtumlType {

	private static final long serialVersionUID = 1L;

	public static Properties deserialize(Object o) throws XtumlException {
		if (o instanceof Properties) {
			return (Properties) o;
		} else {
			throw new NotImplementedException("Type cannot be deserialized");
		}
	}
}
