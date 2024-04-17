package types;

public class Result {
	
	public static Result SUCCESS = new Result(true);
			
	private boolean result = false;
	private APIException error;

	public Result(boolean result) {
		this.result = result;
	}

	public Result(boolean result, APIException error) {
		this.result = result;
		this.error = error;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public APIException getError() {
		return error;
	}

	public void setError(APIException error) {
		this.error = error;
	}

}
