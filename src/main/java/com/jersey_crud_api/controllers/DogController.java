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

import Services.DogService;

@Path("dogs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DogController {
	private DogService dogService = new DogService();
	
	@POST
	public Response add(Dog dog) {
		try {
			dog = dogService.add(dog);
			return Response.status(Status.CREATED).entity("Cachorro criado com êxito com o id -> " + dog.getId()).build();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar cachorro -> " + e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar cachorro").build();
		}
	}
	@GET
	public Response getAll(){
		try {
			List<Dog> dogList = dogService.getAll();
			return Response.status(Status.OK).entity(dogList).build();
			}			
		 catch (Exception e) {
			System.err.println("Erro ao Listar cachorros -> " + e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao Listas cachorros").build();
		}
	}
	@GET
	@Path("{id}")
	public Response getById(@PathParam("id")Integer id) {

		try {
			Dog dog = dogService.getById(id);
			return Response.status(Status.OK).entity(dog).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar cachorro" + e.getMessage()).build();
		}
	}
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Integer id) {
		try {
			boolean work = dogService.delete(id);
			if (work == false) {
				return Response.status(Status.NOT_FOUND).entity("Nenhum cachorro encontrado com esse id").build();	
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao apagar cachorro" + e.getMessage()).build();
		}
	}

	
}
