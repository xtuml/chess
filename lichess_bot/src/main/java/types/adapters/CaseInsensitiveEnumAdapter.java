package types.adapters;

import java.io.IOException;
import java.util.stream.Stream;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class CaseInsensitiveEnumAdapter<E> extends TypeAdapter<E> {

	private final Class<E> enumClass;

	public CaseInsensitiveEnumAdapter(Class<E> enumClass) {
		this.enumClass = enumClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		} else {
			final var value = reader.nextString();
			return (E) getMatchingEnumConstant(enumClass, value);
		}
	}

	@Override
	public void write(JsonWriter out, E value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else if (enumClass.isEnum()) {
			out.value(((Enum<?>) value).name());
		} else {
			out.value(value.toString());
		}
	}

	public static Object getMatchingEnumConstant(Class<?> enumClass, String value) {
		if (enumClass.isEnum()) {
			return Stream.of(enumClass.getEnumConstants())
					.filter(e -> ((Enum<?>) e).name().toLowerCase().equals(value.toLowerCase())).findAny()
					.orElseThrow(() -> new IllegalArgumentException(
							"No enum constant " + enumClass.getName() + "." + value));
		} else {
			return null;
		}
	}

}
