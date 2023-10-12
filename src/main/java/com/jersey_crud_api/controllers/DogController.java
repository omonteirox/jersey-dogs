package com.jersey_crud_api.controllers;

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

import com.jersey_crud_api.models.Dog;
import com.jersey_crud_api.models.ModelResponse;
import com.jersey_crud_api.services.DogService;

@Path("dogs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DogController {
	private DogService dogService = new DogService();
	
	@POST
	public Response add(Dog dog) {
		try {
			dog = dogService.add(dog);
			if (!dog.getNotifications().isEmpty()) {
				return Response.status(Status.NOT_ACCEPTABLE).entity(new ModelResponse<List<String>>(dog.getNotifications().toString())).build();
			}
			return Response.status(Status.CREATED).entity(new ModelResponse<>(dog)).build();
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ModelResponse<>("OMD001 - Erro ao criar Cachorro")).build();
		}
	}
	@GET
	public Response getAll(){
		try {
			List<Dog> dogList = dogService.getAll();
			return Response.status(Status.OK).entity(new ModelResponse<List<Dog>>(dogList)).build();
			}			
		 catch (Exception e) {
			 return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ModelResponse<>("OMD002 - Erro ao listar Cachorros")).build();
		}
	}
	@GET
	@Path("{id}")
	public Response getById(@PathParam("id")Integer id) {

		try {
			Dog dog = dogService.getById(id);
			if (!dog.getNotifications().isEmpty()) {
				return Response.status(Status.NOT_ACCEPTABLE).entity(new ModelResponse<List<String>>(dog.getNotifications().toString())).build();
			}
			return Response.status(Status.OK).entity(new ModelResponse<>(dog)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ModelResponse<>("OMD003 - Erro ao lista Cachorro")).build();
		}
	}
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Integer id) {
		try {
			boolean work = dogService.delete(id);
			if (work == false) {
				return Response.status(Status.NOT_FOUND).entity(new ModelResponse<>("Nenhum cachorro encontrado com esse id")).build();	
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ModelResponse<>("OMD003 - Erro ao apaga rCachorro")).build();
		}
	}

	
}
