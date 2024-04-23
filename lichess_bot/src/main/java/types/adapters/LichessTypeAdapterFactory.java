package types.adapters;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import types.lichess.GameStatus;
import types.lichess.Variant;

public class LichessTypeAdapterFactory implements TypeAdapterFactory {
	
	private final CaseInsensitiveEnumAdapterFactory enumAdapterFactory = new CaseInsensitiveEnumAdapterFactory();

	@SuppressWarnings("unchecked")
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

		if (type.getRawType().equals(Variant.class)) {
			return (TypeAdapter<T>) new VariantAdapter();
		}
		if (type.getRawType().equals(GameStatus.class)) {
			return (TypeAdapter<T>) new GameStatusAdapter();
		}
		if (type.getRawType().equals(List.class)) {
			return (TypeAdapter<T>) new MovesAdapter();
		}
		
		final var enumAdapter = enumAdapterFactory.create(gson, type);
		if (enumAdapter != null) {
			return enumAdapter;
		}

		return null;
	}

}
