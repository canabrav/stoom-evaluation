package com.stoom.service.address.geocode;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResult {
	
	@JsonProperty("address_components")
	private List<AddressComponent> addressComponents = null;

	@JsonProperty("formatted_address")
	private String formattedAddress;

	@JsonProperty("geometry")
	private Geometry geometry;

	@JsonProperty("place_id")
	private String placeId;

	@JsonProperty("plus_code")
	private Map<String, Object> plusCode;
	
	@JsonProperty("types")
	private List<String> types = null;

	
	public List<AddressComponent> getAddressComponents() {
		return addressComponents;
	}

	public void setAddressComponents(List<AddressComponent> addressComponents) {
		this.addressComponents = addressComponents;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Map<String, Object> getPlusCode() {
		return plusCode;
	}

	public void setPlusCode(Map<String, Object> plusCode) {
		this.plusCode = plusCode;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AddressComponent {
		
		@JsonProperty("long_name")
		private String longName;
		
		@JsonProperty("short_name")
		private String shortName;
		
		@JsonProperty("types")
		private List<String> types = null;

		public String getLongName() {
			return longName;
		}

		public void setLongName(String longName) {
			this.longName = longName;
		}

		public String getShortName() {
			return shortName;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public List<String> getTypes() {
			return types;
		}

		public void setTypes(List<String> types) {
			this.types = types;
		}
		
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Geometry {
		
		@JsonProperty("location")
		private Location location;

		@JsonProperty("location_type")
		private String locationType;

		@JsonProperty("viewport")
		private Map<String, Object> viewport;

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public String getLocationType() {
			return locationType;
		}

		public void setLocationType(String locationType) {
			this.locationType = locationType;
		}

		public Map<String, Object> getViewport() {
			return viewport;
		}

		public void setViewport(Map<String, Object> viewport) {
			this.viewport = viewport;
		}
		
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Location {

		@JsonProperty("lat")
		private Double lat;

		@JsonProperty("lng")
		private Double lng;

		public Double getLat() {
			return lat;
		}

		public void setLat(Double lat) {
			this.lat = lat;
		}

		public Double getLng() {
			return lng;
		}

		public void setLng(Double lng) {
			this.lng = lng;
		}
		
	}

	public Double getLatitude() {
		return this.geometry.location.lat;
	}

	public Double getLongitude() {
		return this.geometry.location.lng;
	}
}


