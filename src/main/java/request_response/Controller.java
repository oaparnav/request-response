package request_response;

import java.util.HashMap;
import java.util.Map;

public class Controller {
	private MongoRepository repo;
	private CacheService service;

	public Controller(MongoRepository repo, CacheService service) {
		this.repo = repo;
		this.service = service;
	}

	public Response doCalculation(Request req, String vin) {
		Adapter adapter = new MongoRepositoryAdater(repo.getValues(vin), 
				new RequestAdapter(req, 
						new CacheServiceAdapter(service, new LastAdapter())));

		Map<String, String> mapl = new HashMap<>();
			  req.getKeys().forEach(key -> adapter.getValue(key).ifPresent(value -> mapl.put(key, value))); 
		 
		
		
		Response response = new Response(mapl);
		return response;
	}
}
