package system.lichess.adapters;

public class EnumAdapter {

	public static <T extends Enum<T>> T adapt(Enum<?> o, Class<T> cls) {
		if (o != null && cls != null) {
			return Enum.valueOf(cls, o.name());
		} else {
			return null;
		}
	}

}
