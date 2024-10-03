package lichess.types.adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import lichess.types.Color;
import lichess.types.GameOverview;
import lichess.types.GameStreamItem;
import lichess.types.GameUpdate;
import lichess.types.Player;

public class GameStreamItemAdapter extends TypeAdapter<GameStreamItem> {

	private final LichessTypeAdapterFactory factory;

	public GameStreamItemAdapter(LichessTypeAdapterFactory factory) {
		this.factory = factory;
	}

	@Override
	public GameStreamItem read(JsonReader reader) throws IOException {
		GameStreamItem item = null;
		final GameOverview overview = new GameOverview();
		final GameUpdate update = new GameUpdate();
		reader.beginObject();
		while (reader.hasNext()) {
			final var name = reader.nextName();
			switch (name) {
			case "fen":
				update.setFen(reader.nextString());
				break;
			case "wc":
				update.setWc(reader.nextInt());
				item = update;
				break;
			case "bc":
				update.setBc(reader.nextInt());
				item = update;
				break;
			case "id":
				overview.setId(reader.nextString());
				item = overview;
				break;
			case "status":
				overview.setStatus(new GameStatusAdapter().read(reader));
				item = overview;
				break;
			case "winner":
				overview.setWinner(new CaseInsensitiveEnumAdapter<Color>(Color.class).read(reader));
				item = overview;
				break;
			case "players":
				reader.beginObject();
				while (reader.hasNext()) {
					final var name2 = reader.nextName();
					switch (name2) {
					case "white":
						reader.beginObject();
						while (reader.hasNext()) {
							final var name3 = reader.nextName();
							switch (name3) {
							case "user":
								overview.setWhite(factory.getGson().getAdapter(Player.class).read(reader));
								break;
							default:
								reader.skipValue();
								break;
							}
						}
						reader.endObject();
						break;
					case "black":
						reader.beginObject();
						while (reader.hasNext()) {
							final var name3 = reader.nextName();
							switch (name3) {
							case "user":
								overview.setBlack(factory.getGson().getAdapter(Player.class).read(reader));
								break;
							default:
								reader.skipValue();
								break;
							}
						}
						reader.endObject();
						break;
					default:
						reader.skipValue();
						break;
					}
				}
				reader.endObject();
				break;
			default:
				reader.skipValue();
				break;
			}
		}
		reader.endObject();
		return item;
	}

	@Override
	public void write(JsonWriter writer, GameStreamItem value) throws IOException {
		throw new UnsupportedOperationException("Serializing a GameStreamItem instance is not supported.");
	}

}
