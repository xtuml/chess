package lichess.types.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import lichess.types.GameStatus;
import lichess.types.GameStreamItem;
import lichess.types.Variant;

public class LichessTypeAdapterFactory implements TypeAdapterFactory {
	
	private final CaseInsensitiveEnumAdapterFactory enumAdapterFactory = new CaseInsensitiveEnumAdapterFactory();

	private Gson gson;

	@SuppressWarnings("unchecked")
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

		if (type.getRawType().equals(Variant.class)) {
			return (TypeAdapter<T>) new VariantAdapter();
		}

		if (type.getRawType().equals(GameStatus.class)) {
			return (TypeAdapter<T>) new GameStatusAdapter();
		}

		if ("java.util.List<java.lang.String>".equals(type.getType().getTypeName())) {
			return (TypeAdapter<T>) new MovesAdapter();
		}

		if (type.getRawType().equals(GameStreamItem.class)) {
			return (TypeAdapter<T>) new GameStreamItemAdapter(this);
		}
		
		final var enumAdapter = enumAdapterFactory.create(gson, type);
		if (enumAdapter != null) {
			return enumAdapter;
		}

		return null;
	}
	
	public void setGson(Gson gson) {
		this.gson = gson;
	}
	
	public Gson getGson() {
		return gson;
	}

}
