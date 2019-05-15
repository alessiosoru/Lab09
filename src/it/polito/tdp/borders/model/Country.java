package it.polito.tdp.borders.model;

import javax.print.attribute.standard.MediaSize.Other;

public class Country implements Comparable{
	
	private int countryId;
	private String stateAbbreviation;
	private String stateName;

	public Country(int countryId, String stateAbbreviation, String stateName) {
		this.countryId = countryId;
		this.stateAbbreviation = stateAbbreviation;
		this.stateName = stateName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		return this.countryId+" - "+this.stateAbbreviation+" - "+this.stateName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countryId;
		return result;
	}

	@Override
	public boolean equals(Object obj) { // la chiave orimaria è l'id
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (countryId != other.countryId)
			return false;
		return true;
	}

	@Override
	public int compareTo(Object obj) {
		Country other = (Country) obj;
		return this.stateName.compareTo(other.stateName);
	}

	
}
