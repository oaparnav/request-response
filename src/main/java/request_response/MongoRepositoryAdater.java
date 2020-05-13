package request_response;

import java.util.Optional;

public class MongoRepositoryAdater implements Adapter{
	
	private Document repo;
	private Adapter adapter;
	
	public MongoRepositoryAdater(Document repo, Adapter adapter) {
		this.repo = repo;
		this.adapter = adapter;
	}

	public Optional<String> getValue(String key) {
		try {
			return Optional.of(repo.getDocumentField(key));
		}
		catch(Exception e) {
			return adapter.getValue(key);
		}
		
	}
}
