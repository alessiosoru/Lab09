package it.polito.tdp.borders.model;

public class Border {
	
	private int state1Id;
	private int state2Id;
	private int year;
	
	public Border(int state1Id, int state2Id, int year) {
		this.state1Id = state1Id;
		this.state2Id = state2Id;
		this.year = year;
	}
	
	public int getState1Id() {
		return state1Id;
	}
	public void setState1Id(int state1Id) {
		this.state1Id = state1Id;
	}
	public int getState2Id() {
		return state2Id;
	}
	public void setState2Id(int state2Id) {
		this.state2Id = state2Id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "Border [state1Id=" + state1Id + ", state2Id=" + state2Id + ", year=" + year + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state1Id;
		result = prime * result + state2Id;
		result = prime * result + year;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		if (state1Id != other.state1Id)
			return false;
		if (state2Id != other.state2Id)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	

}
