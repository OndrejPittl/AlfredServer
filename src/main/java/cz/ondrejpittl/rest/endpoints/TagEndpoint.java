package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.services.TagService;
import cz.ondrejpittl.mappers.TagRestMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagEndpoint {

    @Inject
    private TagService tagService;

    @Inject
    private TagRestMapper tagRestMapper;

    @GET
    public Response getAllTags() {
        return Response.ok(tagRestMapper.toDTOs(tagService.getAllTags())).build();
    }

    @GET
    @Path("/{id}")
    public Response getTag(@PathParam("id") final Long id) {
        return Response.ok(tagRestMapper.toDTO(tagService.getTag(id))).build();
    }
}
