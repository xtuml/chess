package lichess.types.adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import lichess.types.Variant;

public class VariantAdapter extends TypeAdapter<Variant> {

	@Override
	public Variant read(JsonReader reader) throws IOException {
		final var nextToken = reader.peek();
		if (nextToken == JsonToken.STRING) {
			return (Variant) CaseInsensitiveEnumAdapter.getMatchingEnumConstant(Variant.class,
					reader.nextString());
		} else {
			Variant value = null;
			reader.beginObject();
			while (reader.hasNext()) {
				final var name = reader.nextName();
				if (name.equals("key")) {
					value = (Variant) CaseInsensitiveEnumAdapter.getMatchingEnumConstant(Variant.class, reader.nextString());
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
			return value;
		}
	}

	@Override
	public void write(JsonWriter writer, Variant value) throws IOException {
		writer.beginObject();
		writer.name("key").value(value.getValue());
		writer.endObject();
	}

}
