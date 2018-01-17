package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.annotations.Secured;
import cz.ondrejpittl.business.services.CommentService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.persistence.domain.Comment;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.PostDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    public Response getComment(@PathParam("id") final Long id) {
        return Response.ok(commentRestMapper.toDTO(commentService.getComment(id))).build();
    }




    // -------------- POST ----------------

    /**
     * // HTTP POST   /comments/post/id
     *
     *
     * @param postId
     * @param comment
     * @return
     */
    @POST
    @Secured
    @Path("/post/{postId}")
    public Response createComment(@PathParam("postId") final Long postId, CommentDTO comment) {
        //Dev.print("COMMENT POST: Endpoint reached.");
        List<Comment> comments = commentService.createComment(postId, comment);
        return Response.ok(this.commentRestMapper.toDTOs(comments)).build();
    }


    // --------------- PUT ---------------

    @PUT
    @Secured
    @Path("/{id}")
    public Response modifyComment(@PathParam("id") final Long id, CommentDTO comment) {
        //Dev.print("COMMENT PUT: Endpoint reached.");
        return Response.ok(this.commentRestMapper.toDTOs(commentService.modifyComment(id, comment))).build();
    }


    // ------------- DELETE ---------------

    /**
     * //  HTTP DELETE   /comments/id
     *
     * Removes the specific comment of ID given via URL.
     * @param id     ID of the comment
     * @return       DTO response
     */
    @DELETE
    @Secured
    @Path("/{id}")
    public Response removeComment(@PathParam("id") final Long id) {
        //Dev.print("COMMENT DELETE: Endpoint reached.");
        return Response.ok(this.commentRestMapper.toDTOs(commentService.removeComment(id))).build();
    }





}
