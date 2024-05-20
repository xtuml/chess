package lichess.types.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class CaseInsensitiveEnumAdapterFactory implements TypeAdapterFactory {

	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		@SuppressWarnings("unchecked")
		Class<T> rawType = (Class<T>) type.getRawType();
		if (!rawType.isEnum()) {
			return null;
		}

		return new CaseInsensitiveEnumAdapter<>(rawType);
	}

}
