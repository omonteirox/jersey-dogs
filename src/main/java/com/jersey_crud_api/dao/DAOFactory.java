package com.jersey_crud_api.dao;

import com.jersey_crud_api.database.DbContext;
import com.jersey_crud_api.services.DogServiceImpl;
import com.jersey_crud_api.services.TutorServiceImpl;

public class DAOFactory {
public static TutorDAO createTutorDAO() {
	return new TutorServiceImpl(DbContext.Connect());
	
}
public static DogDAO createDogDAO() {
	return new DogServiceImpl(DbContext.Connect());
}
}
