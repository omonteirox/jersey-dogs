package com.jersey_crud_api.dao;

import java.util.List;

import com.jersey_crud_api.models.Tutor;

public interface TutorDAO {

	
	public Tutor add(Tutor tutor);
	public List<Tutor> getAll();
	public Tutor getById(Integer id);
	public boolean delete(Integer id);
}
