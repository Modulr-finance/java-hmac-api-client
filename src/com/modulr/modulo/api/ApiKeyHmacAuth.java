package com.modulr.modulo.api;

import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.swagger.client.Pair;
import io.swagger.client.auth.ApiKeyAuth;

public class ApiKeyHmacAuth extends ApiKeyAuth {
	public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	public ApiKeyHmacAuth(String location, String paramName) {
		super(location,paramName);
	}

	@Override
	public void applyToParams(List<Pair> queryParams, Map<String, String> headerParams) {
		if (getApiKey() == null) {
			throw new IllegalStateException("apiKey required for Modulr API Auth");
		}
		
		if(getApiKeyPrefix() == null) {
			throw new IllegalStateException("apiKeyPrefix required for Modulr API Auth");
		}
		
		if ( !"header".equalsIgnoreCase(getLocation())) {
			throw new IllegalStateException(getLocation() + " invalid location for Modulr API Auth");
		} else {
			try {
				String nonce = UUID.randomUUID().toString();
				Date now = new Date();
				DateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				String nowString = sdf.format(now);
				String data = String.format("date: %s\nx-mod-nonce: %s", nowString, nonce);
				String signature = calculateHMAC(data, getApiKeyPrefix());
				
				String auth = String.format("Signature keyId=\"%s\",algorithm=\"%s\",headers=\"date x-mod-nonce\",signature=\"%s\"", getApiKey(), "hmac-sha1", signature);
	
				headerParams.put(getParamName(), auth);
				headerParams.put("Date", nowString);
				headerParams.put("x-mod-nonce", nonce);
			} catch (SignatureException e) {
				e.printStackTrace();
				throw new IllegalStateException("Failed to build Auth headers for Modulr API Auth");
			}
		}
	}

	protected String calculateHMAC(String data, String key) throws SignatureException {
		try {
			key = key.trim();

			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			String hmac = Base64.getEncoder().encodeToString(rawHmac);
			return URLEncoder.encode(hmac, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage(), e);
		}
	}
}
