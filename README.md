# java-hmac-api-client
A java client that handles HMAC auth to Modulr API

## Pre-requisites
1. A Gradle installation
2. Access to Modulr API catalogue ( You can sign up at the [Modulr Developer Portal](https://modulr-technology-ltd.cloud.tyk.io/portal/) )

## Usage
1. Generate a Swagger Java Client for Modulr API
   1. Retrieve the Modulr api definition in json format from the Modulr Developer Portal
   1. Paste this json into the [Online Swagger Editor Demo](http://editor.swagger.io/#/) by using `File -> Paste json` menu
   1. Generate a java client by using `Generate Client -> Java` menu
   1. A gradle project of the swagger java client will be downloaded, build this project by using running `gradle clean assemble`
1. Clone this repository
1. Optionally, modify the [Gradle build file](https://github.com/Modulr-finance/java-hmac-api-client/blob/master/build.gradle) to use the correct path to generated java client from the previous step
    
    ```
    compile files('../swagger-java-client/build/libs/swagger-java-client-' + SWAGGER_JAVA_CLIENT_VERSION + '.jar')
    ```
    
1. Build the hmac client jar using `gradle clean build`
1. Use this jar (at build/libs/java-hmac-api-client-1.0.0.jar ) in your project to interact with the Modulr API. An example is available at [ExampleClient](https://github.com/Modulr-finance/java-hmac-api-client/blob/master/src/java/main/com/example/ExampleClient.java) also reproduced below

	```java
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
	```