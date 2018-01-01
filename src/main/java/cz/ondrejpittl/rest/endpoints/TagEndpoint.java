package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.services.TagService;
import cz.ondrejpittl.mappers.TagRestMapper;
import cz.ondrejpittl.rest.dtos.TagDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/tags")
@Produces("application/json")
@Consumes("application/json")
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
    public Response getUser(@PathParam("id") final Long id) {
        return Response.ok(tagRestMapper.toDTO(tagService.getTag(id))).build();
    }


    @POST
    public Response createTag(TagDTO tag) {
        return Response.ok(tagService.createTag(tag)).build();
    }
}
