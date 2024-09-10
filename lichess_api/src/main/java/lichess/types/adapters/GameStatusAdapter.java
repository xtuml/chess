package lichess.types.adapters;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import lichess.types.GameStatus;

public class GameStatusAdapter extends CaseInsensitiveEnumAdapter<GameStatus> {

	public GameStatusAdapter() {
		super(GameStatus.class);
	}

	@Override
	public GameStatus read(JsonReader reader) throws IOException {
		final var nextToken = reader.peek();
		if (nextToken == JsonToken.STRING) {
			return (GameStatus) CaseInsensitiveEnumAdapter.getMatchingEnumConstant(GameStatus.class,
					reader.nextString());
		} else {
			GameStatus value = null;
			reader.beginObject();
			while (reader.hasNext()) {
				final var name = reader.nextName();
				if (name.equals("id")) {
					value = GameStatus.fromValue(reader.nextInt());
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
			return value;
		}
	}

	@Override
	public void write(JsonWriter writer, GameStatus value) throws IOException {
		writer.beginObject();
		writer.name("id").value(value.getValue());
		writer.name("name").value(value.name());
		writer.endObject();
	}

}
