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

import com.jersey_crud_api.models.Tutor;

import Services.TutorService;

@Path("tutors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TutorController {
	private TutorService tutorService = new TutorService();
	@POST
	public Response add(Tutor tutor) {
		try {
			tutor = tutorService.add(tutor);
			return Response.status(Status.CREATED).entity("Tutor criado com êxito com o id -> " + tutor.getId()).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar tutor" + e.getMessage()).build();
		}
	}
	@GET
	public Response getAll(){
		try {
			List<Tutor> dogList = tutorService.getAll();		
			return Response.status(Status.OK).entity(dogList).build();
		} catch (Exception e) {
			System.err.println("Erro ao Listar tutores -> " + e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao Listas tutores").build();
		}
	}
	@GET
	@Path("{id}")
	public Response getById(@PathParam("id")Integer id) {
		try {
			Tutor tutor = tutorService.getById(id);
			return Response.status(Status.OK).entity(tutor).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar tutor" + e.getMessage()).build();
		}
		
	}
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Integer id) {
		try {
			boolean delete = tutorService.delete(id);
			if (delete == false) {
				return Response.status(Status.NOT_FOUND).entity("Nenhum tutor encontrado com esse id").build();	
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao apagar tutor" + e.getMessage()).build();
		}
	}
	
}
