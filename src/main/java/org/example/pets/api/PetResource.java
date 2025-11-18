package org.example.pets.api;

import org.example.pets.dto.PetDTO;
import org.example.pets.service.PetService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.Collection;

@Path("/pets")   //Tar emot och och returnerar JSON.
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    PetService service;

    @GET
    public Collection<PetDTO> list() {  //Hämtar alla djur och returnerar listan i JSON. (Alla djur plus dess info)
        return service.findAll();
    }

    @POST   //Skapar nytt husdjur.
    public Response adopt(@Valid PetDTO dto, @Context UriInfo uri) {
        PetDTO saved = service.create(dto);
        URI location = uri.getAbsolutePathBuilder().path(String.valueOf(saved.getId())).build();
        return Response.created(location).entity(saved).build();
    }

    @GET @Path("{id}")  //Hämtar ETT husdjur.
    public PetDTO get(@PathParam("id") long id) {
        return service.find(id).orElseThrow(NotFoundException::new);
    }

    @PUT @Path("{id}/feed")
    public PetDTO feed(@PathParam("id") long id) {
        return service.feed(id, 10);
    }

    @PUT @Path("{id}/play")
    public PetDTO play(@PathParam("id") long id) {
        return service.play(id, 10);
    }

    @DELETE @Path("{id}")
    public void release(@PathParam("id") long id) {
        service.delete(id);
    }
}
