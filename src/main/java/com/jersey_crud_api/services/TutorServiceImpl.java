package com.jersey_crud_api.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jersey_crud_api.dao.TutorDAO;
import com.jersey_crud_api.database.DbContext;
import com.jersey_crud_api.exceptions.DbException;
import com.jersey_crud_api.models.Dog;
import com.jersey_crud_api.models.Tutor;

public class TutorServiceImpl implements TutorDAO {
	private Connection connection;
	public TutorServiceImpl(Connection connection) {
		this.connection = connection;
	}
	public Tutor add(Tutor tutor) {
		connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DbContext.Connect();
			String query = "insert into tutor (name) values (?)";
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, tutor.getName());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				tutor.setId(resultSet.getInt("id"));
			}
			return tutor;
			
		}
		catch(SQLException e) {
		}
		catch (Exception e) {
			}
		finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
	}
	public List<Tutor> getAll(){
		connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Tutor> tutorList = new ArrayList<Tutor>();
		String query = "select * from tutor";
		Tutor tutor = new Tutor();
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				tutor = new Tutor();
				tutor.setId(resultSet.getInt(1));
				tutor.setName(resultSet.getString(2));
				tutor.setDogs(getDogsOfTutor(tutor.getId()));
				tutorList.add(tutor);
			}	
			
			return tutorList;
		}catch(SQLException e) {
		}
		catch (Exception e) {
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
		
	}
	
	public Tutor getById(Integer id)  {
		connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from tutor where id = ?";
		Tutor tutor = new Tutor();
		try {
			List<Dog> dogs = getDogsOfTutor(id);
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				tutor.setId(resultSet.getInt(1));
				tutor.setName(resultSet.getString(2));
				tutor.setDogs(dogs);
				System.out.println(tutor.toString());
			}
			return tutor;
		}catch(SQLException e) {
		}
		catch (Exception e) {
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
	}
	public boolean delete(Integer id){
		connection = null;
		PreparedStatement preparedStatement = null;
		String query = "delete from tutor where id = ?";
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected == 0) {
				return false;
			}
			return true;
		} catch(SQLException e) {
		}
		catch (Exception e) {
			}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.Disconnect(connection);
		}
		return false;
	}
	public List<Dog> getDogsOfTutor(Integer id) throws Exception{
		connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Dog> dogs = new ArrayList<Dog>();
		String query = "select * from dog where tutor_id = ?";
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Dog dog = new Dog();
				dog.setId(resultSet.getInt("id"));
				dog.setBirthDate(resultSet.getDate("birth_date"));
				dog.setName(resultSet.getString("name"));
				dog.setRace(resultSet.getString("race"));
				dog.setColor(resultSet.getString("color"));
				dog.setTutorId(resultSet.getInt("tutor_id"));
				dogs.add(dog);
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage(), e.getCause());
		}
		catch (Exception e) {
			throw new Exception(e.getMessage(), e.getCause());
			}
		finally {
			DbContext.Disconnect(connection);
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
		}
		return dogs;
	}
	
}
