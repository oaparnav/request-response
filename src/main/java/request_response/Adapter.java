package request_response;

import java.util.Optional;

public interface Adapter {
	public Optional<String> getValue(String key);
}
