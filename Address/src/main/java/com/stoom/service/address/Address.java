package com.stoom.service.address;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import com.stoom.service.address.exception.ValidationException;

/**
 * An JPA entity to represent the Address.
 * 
 * @author Rodrigo
 *
 */
@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 4037948249134985088L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String streetName;

	@Column(nullable = false)
	private String number;

	@Column(nullable = true)
	private String complement;

	@Column(nullable = false)
	private String neighbourhood;
	
	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private String country;

	@Column(nullable = false)
	private String zipCode;

	@Column(nullable = true)
	private String latitude;

	@Column(nullable = true)
	private String longitude;


	public Address() {
		super();
	}

	

	public Address(String streetName, String number, String complement, String neighbourhood, String city, String state,
			String country) {
		super();
		this.streetName = streetName;
		this.number = number;
		this.complement = complement;
		this.neighbourhood = neighbourhood;
		this.city = city;
		this.state = state;
		this.country = country;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", streetName=" + streetName + ", number=" + number + ", complement=" + complement
				+ ", neighbourhood=" + neighbourhood + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", zipCode=" + zipCode + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}




	private int hashCodeFromAttributes() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((complement == null) ? 0 : complement.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((neighbourhood == null) ? 0 : neighbourhood.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}



	@Override
	public int hashCode() {
		if (id == null) return hashCodeFromAttributes();
		else {
			final int prime = 31;
			int result = 1;
			result = prime * result + id.hashCode();
			return result;
		}
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (id != null && other.id != null) {
			return id.equals(other.id);
		} else if (id == null && other.id == null) {
			return hasEqualAttributes(other);
		} else {
			return false;
		}
		
	}



	private boolean hasEqualAttributes(Address other) {
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (complement == null) {
			if (other.complement != null)
				return false;
		} else if (!complement.equals(other.complement))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (neighbourhood == null) {
			if (other.neighbourhood != null)
				return false;
		} else if (!neighbourhood.equals(other.neighbourhood))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (streetName == null) {
			if (other.streetName != null)
				return false;
		} else if (!streetName.equals(other.streetName))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}


	

	/*
	 * non-javadoc
	 * 
	 * builder methods in fluent interface style
	 */
	
	public Address withZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}

	public Address withLatLong(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		return this;
	}

	public Address withId(Long id) {
		this.id = id;
		return this;
	}


	public void validate() throws ValidationException {
		for (Field field: this.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Column.class)) {
				Column annotation = field.getAnnotation(Column.class);
				if (!annotation.nullable() && isEmpty(valueFrom(field))) {
					throw new ValidationException(String.format("Field '%s' is mandatory", field.getName()));
				}
			}
		}
		
	}


	private Object valueFrom(Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(this);
            field.setAccessible(false);
            return value;
        } catch (Exception e) {
        	// should not get here
            throw new RuntimeException(e);
        }
    }

	
	private boolean isEmpty(Object object) {
		return object == null || (object instanceof CharSequence && StringUtils.isBlank(object.toString()));
	}

}
