package types.adapters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class MovesAdapter extends TypeAdapter<List<String>> {

	@Override
	public List<String> read(JsonReader reader) throws IOException {
		final var moveString = reader.nextString();
		return !moveString.isEmpty() ? List.of(moveString.split("\\s+")) : List.of();
	}

	@Override
	public void write(JsonWriter writer, List<String> value) throws IOException {
		writer.value(value.stream().collect(Collectors.joining(" ")));
	}

}
