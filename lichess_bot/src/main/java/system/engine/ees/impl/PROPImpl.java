package system.engine.ees.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.Utility;
import system.engine.ees.PROP;
import types.Properties;

public class PROPImpl<C extends IComponent<C>> extends Utility<C> implements PROP {

	public PROPImpl(C context) {
		super(context);
	}

	@Override
	public Properties loadProperties(final String fileName) {
		return lib.PROP.loadProperties(fileName, new Properties());
	}

	public boolean getBoolean(final Properties properties, final String name, final boolean defaultValue) {
		return lib.PROP.getBoolean(properties, name, defaultValue);
	}

	public int getInteger(final Properties properties, final String name, final int defaultValue) {
		return lib.PROP.getInteger(properties, name, defaultValue);
	}

	public String getString(final Properties properties, final String name, final String defaultValue) {
		return lib.PROP.getString(properties, name, defaultValue);
	}

}
