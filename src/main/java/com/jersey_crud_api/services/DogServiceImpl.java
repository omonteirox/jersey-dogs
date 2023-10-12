package com.jersey_crud_api.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jersey_crud_api.dao.DogDAO;
import com.jersey_crud_api.database.DbContext;
import com.jersey_crud_api.models.Dog;

public class DogServiceImpl implements DogDAO{
	private Connection connection;
	public DogServiceImpl(Connection connection) {
		this.connection = connection;
	}
	
	public Dog add(Dog dog)  {
		connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DbContext.Connect();
			String query = "insert into dog (name,birth_date,race,color,tutor_id) values (?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, dog.getName());
			preparedStatement.setDate(2, dog.getBirthDate());
			preparedStatement.setString(3, dog.getRace());
			preparedStatement.setString(4, dog.getColor());
			preparedStatement.setInt(5, dog.getTutorId());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				dog.setId(resultSet.getInt("id"));
			}
			return dog;
		} catch(SQLException e) {
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
	public List<Dog> getAll() {
		connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Dog> dogList = new ArrayList<Dog>();
		try {
			connection = DbContext.Connect();
			String query = "select id,name,birth_date,race,color,tutor_id from dog";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Dog dog = new Dog();
				dog.setId(resultSet.getInt("id"));
				dog.setName(resultSet.getString("name"));
				dog.setBirthDate(resultSet.getDate("birth_date"));
				dog.setRace(resultSet.getString("race"));
				dog.setColor(resultSet.getString("color"));
				dog.setTutorId(resultSet.getInt("tutor_id"));
				dogList.add(dog);
			}
			return dogList;
		} catch(SQLException e) {
		}
		catch (Exception e) {
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
	}
	public Dog getById(Integer id)  {
		connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select id,name,birth_date,race,color from dog where id = ?";
		Dog dog = new Dog();
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				dog.setId(resultSet.getInt("id"));
				dog.setName(resultSet.getString("name"));
				dog.setBirthDate(resultSet.getDate("birth_date"));
				dog.setRace(resultSet.getString("race"));
				dog.setRace(resultSet.getString("color"));
			}
			return dog;
		} catch(SQLException e) {
		}
		catch (Exception e) {
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
	}
	public boolean delete(Integer id)  {
		connection = null;
		PreparedStatement preparedStatement = null;
		String query = "delete from dog where id = ?";
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected == 0) {
				System.err.println("Erro ao deletar cachorros -> ");
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
	public List<Dog> getByTutorId(Integer id)  {
		connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select id,name,birth_date,race,color,tutor_id from dog where tutor_id = ?";
		List<Dog> dogs = new ArrayList<Dog>();
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Dog dog = new Dog();
				dog.setId(resultSet.getInt("id"));
				dog.setName(resultSet.getString("name"));
				dog.setBirthDate(resultSet.getDate("birth_date"));
				dog.setRace(resultSet.getString("race"));
				dog.setColor(resultSet.getString("color"));
				dog.setTutorId(resultSet.getInt("tutor_id"));
				dogs.add(dog);
			}
			return dogs;
		} catch(SQLException e) {
		}
		catch (Exception e) {
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
	}
}
