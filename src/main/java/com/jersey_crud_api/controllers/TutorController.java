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

import com.jersey_crud_api.dao.DAOFactory;
import com.jersey_crud_api.dao.TutorDAO;
import com.jersey_crud_api.models.ModelResponse;
import com.jersey_crud_api.models.Tutor;

@Path("tutors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TutorController {
	private TutorDAO tutorService;
	public TutorController() {
		tutorService = DAOFactory.createTutorDAO();
	}
	@POST
	public Response add(Tutor tutor) {
		try {
			tutor = tutorService.add(tutor);
			return Response.status(Status.CREATED).entity(new ModelResponse<Tutor>(tutor)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ModelResponse<>("OMT001 - Erro ao criar Tutor")).build();
		}
	}
	@GET
	public Response getAll(){
		List<Tutor> dogList;
		try {
			dogList = tutorService.getAll();		
			if (dogList == null) {
				return Response.status(Status.NO_CONTENT).entity(new ModelResponse<List<Tutor>>(dogList)).build();
			}
				}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ModelResponse<>("OMT002 - Erro ao listar Tutores")).build();
		}
		return Response.status(Status.OK).entity(dogList).build();
	}
	@GET
	@Path("{id}")
	public Response getById(@PathParam("id")Integer id) {
		try {
			Tutor tutor = tutorService.getById(id);
			if (tutor.getId() == null) {
				return Response.status(Status.NOT_FOUND).entity(new ModelResponse<>("Tutor não encontrado")).build();
			}
			return Response.status(Status.OK).entity(new ModelResponse<Tutor>(tutor)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ModelResponse<>("OMT003 - Erro ao listar Tutor")).build();
		}
		
	}
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Integer id) {
		try {
			boolean delete = tutorService.delete(id);
			if (delete == false) {
				return Response.status(Status.NOT_FOUND).entity(new ModelResponse<>("Tutor não encontrado")).build();	
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ModelResponse<>("OMT004 - Erro ao listar Tutores")).build();
		}
	}
	
}
