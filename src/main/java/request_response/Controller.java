package request_response;

public class Controller {
	private MongoRepository repo;
	private CacheService service;

	public Controller(MongoRepository repo, CacheService service) {
		this.repo = repo;
		this.service = service;
	}

	public Response doCalculation(Request req, String vin) {
		return null;
	}
}
