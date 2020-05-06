package request_response;

import java.util.HashMap;
import java.util.Map;

public class Document {
	private Map<String, String> map;
	
	public Document() {
		map = new HashMap<>();
	}
	
	public Document addField(String key, String value) {
		map.put(key, value);
		return this;
	}
	
	public String getDocumentField(String key) {
		return map.get(key);
	}

}
