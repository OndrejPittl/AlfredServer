package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.services.PostService;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.mappers.PostRestMapper;
import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.rest.dtos.PostDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/posts")
@Produces("application/json")
@Consumes("application/json")
public class PostEndpoint {

    @Inject
    private PostService postService;

    @Inject
    private PostRestMapper postRestMapper;

    @GET
    public Response getAllPosts() {
        return Response.ok(postRestMapper.toDTOs(postService.getAllPosts())).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") final Long id) {
        return Response.ok(postRestMapper.toDTO(postService.getPost(id))).build();
    }

    @POST
    public Response createPost(PostDTO post) {
        return Response.ok(postService.createPost(post)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removePost(@PathParam("id") final Long id) {
        return Response.ok(postService.removePost(id)).build();
    }
}
