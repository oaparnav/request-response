package request_response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	
	@Test
	public void ifNothingHasDefaultKeyShouldNotBeInResponse() {
		String vin = "vin-1";
		Document document = new Document();
		when(repo.getValues(vin)).thenReturn(document);
		Request req = new Request();
		req.addField("model");
		Response res = c.doCalculation(req, vin);
		assertFalse(res.getKeys().contains("model"));
	}
	
	@Test
	public void checkDBHasHighestPriority() {
		String vin = "vin-1";
		Document document = new Document().addField("model", "Ecosport");
		when(repo.getValues(vin)).thenReturn(document);
		Request req = new Request();
		req.addFieldWithDefault("model", "Endeavour");
		Response res = c.doCalculation(req, vin);
		assertEquals(res.getFieldValue("model"), "Ecosport");
	}

	@Test
	public void checkRequestHasSecondPriority() {
		String vin = "vin-1";
		Document document = new Document();
		when(repo.getValues(vin)).thenReturn(document);
		Request req = new Request();
		req.addFieldWithDefault("model", "Endeavour");
		Response res = c.doCalculation(req, vin);
		assertEquals(res.getFieldValue("model"), "Endeavour");
	}

	@Test
	public void combinedTest() {
		String vin = "vin-1";
		Document document = new Document().addField("model", "Ecosport");
		when(repo.getValues(vin)).thenReturn(document);
		when(cache.getDefault("isElectric")).thenReturn("true");
		Request req = new Request();
		req.addField("model");
		req.addFieldWithDefault("colour", "red");
		req.addField("isElectric");
		req.addField("canRemoteStart");
		Response res = c.doCalculation(req, vin);
		assertEquals(res.getFieldValue("model"), "Ecosport");
		assertEquals(res.getFieldValue("colour"), "red");
		assertEquals(res.getFieldValue("isElectric"), "true");
		assertFalse(res.getKeys().contains("canRemoteStart"));
	}
	
	@Test
	public void shouldSkipCacheIfItReturnsNetworkError() {
		String vin = "vin-1";
		Document document = new Document();
		when(repo.getValues(vin)).thenReturn(document);
		when(cache.getDefault("model")).thenThrow(new RuntimeException());
		Request req = new Request();
		req.addField("model");
		Response res = c.doCalculation(req, vin);
		assertFalse(res.getKeys().contains("model"));
	}
	
}
