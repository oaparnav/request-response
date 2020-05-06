package request_response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {
	@Mock
	private MongoRepository repo;

	@Mock
	private CacheService cache;
	
	@InjectMocks
	Controller c;

	/*
	 * - Request has a list of fields
	 * - Check the Mongo DB for the values of the fields
	 * - If the Mongo DB doesn't have a value for a field then
	 *     - Check if the request has specified a default value, return that
	 *     - If request doesn't have, then check if cache has, return that
	 *     - If no value and DB and no default value, then skip 
	 *       that field in response
	 */
	
	@Test
	public void ifTheRequestedFieldIsThereInDBThenReturnThatValue() {
		String vin = "vin-1";
		Document document = new Document()
				.addField("model", "Ecosport");
		when(repo.getValues(vin)).thenReturn(document);
		Request req = new Request();
		req.addField("model");
		Response res = c.doCalculation(req, vin);
		assertEquals(res.getFieldValue("model"), "Ecosport");
	}
	
	@Test
	public void ifNotInDBThenReturnTheDefaultPassedInTheRequest() {
		String vin = "vin-1";
		Document document = new Document()
				.addField("colour", "blue");
		when(repo.getValues(vin)).thenReturn(document);
		Request req = new Request();
		req.addFieldWithDefault("model", "Ecosport");
		Response res = c.doCalculation(req, vin);
		assertEquals(res.getFieldValue("model"), "Ecosport");
	}

	@Test
	public void ifRequestDoesntHaveDefaultThenCheckCacheForDefaultValue() {
		String vin = "vin-1";
		Document document = new Document()
				.addField("colour", "blue");
		when(repo.getValues(vin)).thenReturn(document);
		Request req = new Request();
		req.addField("model");
		when(cache.getDefault("model")).thenReturn("Ecosport");
		Response res = c.doCalculation(req, vin);
		assertEquals(res.getFieldValue("model"), "Ecosport");
	}
	
}
