package com.jersey_crud_api.dao;

import java.util.List;

import com.jersey_crud_api.models.Dog;


public interface DogDAO {
	public Dog add(Dog tutor);
	public List<Dog> getAll();
	public Dog getById(Integer id);
	public boolean delete(Integer id);
}
