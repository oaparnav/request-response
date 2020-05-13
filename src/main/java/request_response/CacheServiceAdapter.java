package request_response;

import java.util.Optional;

public class CacheServiceAdapter implements Adapter{
	
	private CacheService cacheService;
	private Adapter adapter;

	public CacheServiceAdapter(CacheService cacheService, Adapter adapter) {
		this.cacheService = cacheService;
		this.adapter = adapter;
	}

	public Optional<String> getValue(String key) {
		try {
			return Optional.of(cacheService.getDefault(key));
		}
		catch(Exception e) {
			return adapter.getValue(key);
		}
		
	}
}
