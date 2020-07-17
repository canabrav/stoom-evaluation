package com.stoom.service.address.geocode;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents Google Geocode response.
 * 
 * @author Rodrigo
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeApiResponse {


	@JsonProperty("error_message")
	private String errorMessage;

	private List<GeocodeResult> results = null;

	private String status;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<GeocodeResult> getResults() {
		return results;
	}

	public void setResults(List<GeocodeResult> results) {
		this.results = results;
	}

	public GeocodeResult getFirstResult() {
		if (results != null && !results.isEmpty()) {
			return results.iterator().next();
		}
		return null;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isOk() {
		return "OK".equals(status) && StringUtils.isBlank(errorMessage);
	}

}

