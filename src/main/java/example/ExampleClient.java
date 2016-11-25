package example;

import com.modulr.modulo.api.ModuloApiClient;
import com.modulr.modulo.api.ModuloErrorException;

import io.swagger.client.ApiClient;
import io.swagger.client.Configuration;
import io.swagger.client.api.PaymentsApi;

public class ExampleClient {

	private static final String API_KEY = "<<Your api key>>";
	private static final String HMAC_SECRET = "<<Your hmac secret>>";
	private static final String API_BASE_URL = "https://api-sandbox.modulrfinance.com/api-sandbox/";

	public static void main(String[] args) throws Exception {
		ApiClient apiClient = new ModuloApiClient();
		apiClient.setApiKey(API_KEY);
		apiClient.setApiKeyPrefix(HMAC_SECRET);
		apiClient.setBasePath(API_BASE_URL);
				
		Configuration.setDefaultApiClient(apiClient);

		try {
			System.out.println(new PaymentsApi().getPaymentDetails("P1100005X9"));
		} catch (ModuloErrorException e) {
			System.err.println("API Exception:" + e.getErrors());
		}
	}
}
