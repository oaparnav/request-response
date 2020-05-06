package request_response;

import java.util.Map;
import java.util.Set;

public class Response {
	private Map<String, String> responseValues;

	public Response(Map<String, String> responseValues) {
		this.responseValues = responseValues;
	}
	
	public Set<String> getKeys() {
		return this.responseValues.keySet();
	}
	
	public String getFieldValue(String key) {
		return responseValues.get(key);
	}

}
