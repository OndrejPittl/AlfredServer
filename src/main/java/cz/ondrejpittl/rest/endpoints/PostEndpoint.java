package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.annotations.Secured;
import cz.ondrejpittl.business.services.CommentService;
import cz.ondrejpittl.business.services.PostService;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.mappers.PostRestMapper;
import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.rest.dtos.CommentDTO;
import cz.ondrejpittl.rest.dtos.PostDTO;
import cz.ondrejpittl.rest.dtos.TokenDTO;
import sun.tools.jstat.Token;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostEndpoint {

    @Inject
    private PostService postService;

    @Inject
    private PostRestMapper postRestMapper;



    @Inject
    private CommentService commentService;

    @Inject
    private CommentRestMapper commentMapper;


    // --------------- GET ----------------

    /**
     * Gel all posts
     * @return  DTO response
     */
    @GET
    public Response getAllPosts() {
        return Response.ok(postRestMapper.toDTOs(postService.getAllPosts())).build();
    }

    /**
     * Get specific post defined by ID.
     * @param id    id of a post given via URL
     * @return      DTO response
     */
    @GET
    @Path("/{id}")
    public Response getPost(@PathParam("id") final Long id) {
        return Response.ok(postRestMapper.toDTO(postService.getPost(id))).build();
    }


    /*
    ///
     // Get all coments of the specific post defined by ID.
     // @param id    id of a post given via URL
     // @return      DTO response
     //
    @GET
    @Path("/{id}/comments")
    public Response getComments(@PathParam("id") final Long id) {
        //return Response.ok(postRestMapper.toDTO(postService.getPost(id))).build();
        return Response.ok(commentMapper.toDTOs(commentService.getAllComments())).build();
    }
    */


    // -------------- POST ----------------

    /**
     * Creates a new post.
     * @param post  PostDTO (json) transferred in body.
     * @return      DTO response
     */
    @POST
    @Secured
    public Response createPost(PostDTO post) {
        return Response.ok(postService.createPost(post)).build();
    }




    // --------------- PUT ---------------

    @PUT
    @Secured
    @Path("/{id}")
    public Response modifyPost(@PathParam("id") final Long id, PostDTO post) {
        Dev.print("POST PUT: Endpoint reached.");
        return Response.ok(postService.modifyPost(id, post)).build();
    }


    // -------------- DELETE -------------

    /**
     * Removes a post.
     * @param id    id of a post given via URL
     * @return      DTO response
     */
    @DELETE
    @Secured
    @Path("/{id}")
    public Response removePost(@PathParam("id") final Long id) {
        return Response.ok(postService.removePost(id)).build();
    }
}
