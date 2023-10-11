package com.jersey_crud_api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tutor implements Serializable {

	public Tutor() {
		this.dogs = new ArrayList<Dog>();
	}
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private List<Dog> dogs;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Dog> getDogs() {
		return dogs;
	}
	public void setDogs(List<Dog> dogs) {
		this.dogs = dogs;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Tutor [id=" + id + ", name=" + name + ", dogs=" + dogs + "]";
	}

}
