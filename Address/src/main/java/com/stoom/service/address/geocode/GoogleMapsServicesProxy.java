package com.stoom.service.address.geocode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.stoom.common.HttpClientAdapter;
import com.stoom.common.Json;
import com.stoom.service.address.Address;

/**
 * Proxy class for Google Maps Services.
 * 
 * @author Rodrigo
 *
 */
@Component
public class GoogleMapsServicesProxy {

	@Value("${google.geocode.url}")
	private String googleUrl;

	@Value("${google.geocode.key}")
	private String googleKey;

	private Log logger = LogFactory.getLog(getClass());

	public GoogleMapsServicesProxy() {
	}

	public void fillLatLong(Address address) {
		try {
			HttpClientAdapter client = new HttpClientAdapter(String.format(googleUrl, address.toGoogleString(), googleKey));
			String payload = client.get();
			
			GeocodeApiResponse response = Json.parse(payload, GeocodeApiResponse.class);
			if (!response.isOk() ) {
				logger.warn("Google Maps Api call failed. See response");
				logger.warn(response);
				return; // quit without lat/long
			}
			
			GeocodeResult result = response.getFirstResult();
			address.setLatitude(result.getLatitude());
			address.setLongitude(result.getLongitude());

		} catch (JsonParseException | JsonMappingException e) {
			logger.warn("Error parsing Google Response", e);


		} catch (Exception e) {
			// Credentials may expire, and Google Services may refuse requests.
			// Do not prevent data insertion.
			logger.warn("Exception trying to call Google Geocode services", e);

			
		}

	}

}
