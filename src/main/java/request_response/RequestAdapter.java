package request_response;

import java.util.Optional;

public class RequestAdapter implements Adapter{
	
	private Request request;
	private Adapter adapter;


	public RequestAdapter(Request request,Adapter adapter) {
		 this.request = request;
		 this.adapter = adapter;
	}

	
	public Optional<String> getValue(String key) {
		String value;
		try {
			return Optional.of(request.getDefault(key));
		}
		catch(Exception e) {
			return adapter.getValue(key);
		}
		
	}
}
