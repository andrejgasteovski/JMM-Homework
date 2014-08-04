package com.finki.jmm.homework.database;

public class Student {
	private long id;
	private String name;
	private String index;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	
	public String toString(){
		return name + " " + index;
	}
}
