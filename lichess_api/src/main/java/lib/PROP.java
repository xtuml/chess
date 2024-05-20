package lib;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.Properties;

public class PROP {
	
	public static Properties loadProperties(String fileName) {
		final var props = new Properties();
		return loadProperties(fileName, props);
	}

	public static <T extends Properties> T loadProperties(String fileName, T props) {
		try {
			props.load(PROP.class.getClassLoader().getResourceAsStream(fileName));
			return props;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public static String getString(Properties properties, String name, String defaultValue) {
		if (properties != null) {
			return Optional.ofNullable(properties.getProperty(name)).orElse(defaultValue);
		} else {
			throw new IllegalArgumentException("Properties object is null");
		}
	}
	
	public static int getInteger(Properties properties, String name, int defaultValue) {
		if (properties != null) {
			return Optional.ofNullable(Integer.parseInt(properties.getProperty(name, Integer.toString(defaultValue)))).orElse(defaultValue);
		} else {
			throw new IllegalArgumentException("Properties object is null");
		}
	}
	
	public static boolean getBoolean(Properties properties, String name, boolean defaultValue) {
		if (properties != null) {
			return Optional.ofNullable(Boolean.parseBoolean(properties.getProperty(name, Boolean.toString(defaultValue)))).orElse(defaultValue);
		} else {
			throw new IllegalArgumentException("Properties object is null");
		}
	}

}
