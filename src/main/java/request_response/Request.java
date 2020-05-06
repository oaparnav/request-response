package request_response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Request {
	Map<String, String> requestParams;
	
	public Request() {
		requestParams = new HashMap<>();
	}
	
	public void addField(String key) {
		requestParams.put(key, null);
	}

	public void addFieldWithDefault(String key, String defaultValue) {
		requestParams.put(key, defaultValue);
	}

	public Set<String> getKeys() {
		return requestParams.keySet();
	}
	
	public String getDefault(String key) {
		return requestParams.get(key);
	}
}
