package com.jersey_crud_api.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jersey_crud_api.database.DbContext;
import com.jersey_crud_api.models.Dog;

@Path("dogs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DogController {
	private Connection connection;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	@POST
	public Response add(Dog dog) {
		try {
			connection = DbContext.Connect();
			String query = "insert into dogs (name) values (?)";
			preparedStatement = connection.prepareStatement(query, preparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, dog.getName());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				dog.setId(resultSet.getInt("id"));
			}
			
		} catch (Exception e) {
			System.err.println("Erro ao adicionar cachorro -> " + e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar cachorro").build();
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return Response.status(Status.CREATED).entity("Cachorro criado com êxito com o id -> " + dog.getId()).build();
	}
	@GET
	public Response getAll(){
		List<Dog> dogList = new ArrayList<Dog>();
		try {
			connection = DbContext.Connect();
			String query = "select id,name from dogs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Dog dog = new Dog();
				dog.setId(resultSet.getInt("id"));
				dog.setName(resultSet.getString("name"));
				dogList.add(dog);
			}			
		} catch (Exception e) {
			System.err.println("Erro ao Listar cachorros -> " + e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao Listas cachorros").build();
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return Response.status(Status.OK).entity(dogList).build();
	}
	@GET
	@Path("{id}")
	public Response getById(@PathParam("id")Integer id) {
		String query = "select id,name from dogs where id = ?";
		Dog dog = new Dog();
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				dog.setId(resultSet.getInt("id"));
				dog.setName(resultSet.getString("name"));
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar cachorro" + e.getMessage()).build();
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return Response.status(Status.OK).entity(dog).build();
	}
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Integer id) {
		String query = "delete from dogs where id = ?";
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected == 0) {
				return Response.status(Status.NOT_FOUND).entity("Nenhum cachorro encontrado com esse id").build();	
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao apagar cachorro" + e.getMessage()).build();
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
	}
	
}
