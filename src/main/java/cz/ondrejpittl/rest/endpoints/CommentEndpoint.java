package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.services.CommentService;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.rest.dtos.CommentDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/comments")
@Produces("application/json")
@Consumes("application/json")
public class CommentEndpoint {

    @Inject
    private CommentService commentService;

    @Inject
    private CommentRestMapper commentRestMapper;

    @GET
    public Response getAllComments() {
        return Response.ok(commentRestMapper.toDTOs(commentService.getAllComments())).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") final Long id) {
        return Response.ok(commentRestMapper.toDTO(commentService.getComment(id))).build();
    }

    @POST
    public Response createComment(CommentDTO comment) {
        return Response.ok(commentService.createComment(comment)).build();
    }
}
