package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.services.CommentService;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.rest.dtos.CommentDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentEndpoint {

    @Inject
    private CommentService commentService;

    @Inject
    private CommentRestMapper commentRestMapper;




    // --------------- GET ----------------

    /**
     * Get all comments.
     * @return  DTO response
     */
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
        //return Response.ok(commentService.createComment(comment)).build();
        return null;
    }

    // DELETE   /comments/id
    // POST     /comments/post/id
    //
}
