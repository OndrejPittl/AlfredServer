package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.annotations.ExistingPost;
import cz.ondrejpittl.business.annotations.ExistingUser;
import cz.ondrejpittl.business.annotations.Secured;
import cz.ondrejpittl.business.services.CommentService;
import cz.ondrejpittl.business.services.PostService;
import cz.ondrejpittl.business.services.RatingService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.mappers.PostRestMapper;
import cz.ondrejpittl.rest.dtos.PostDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
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
    private RatingService ratingService;


    @Inject
    private CommentService commentService;

    @Inject
    private CommentRestMapper commentMapper;


    // --------------- GET ----------------

    /**
     * Gel a subset of posts at defined offset via url.
     * @param offset    offset defined via url
     * @return          DTO response
     */
    @GET
    public Response getPosts(
            @QueryParam("offset")
            @Min(value = 0, message = "post.offset.negative") int offset,

            @QueryParam("tag")
            @Size(min = 1, message = "post.tag.size") String tag,

            @QueryParam("rating")
            @Min(value = 0, message = "post.rating.negative") Long rating,

            @QueryParam("photo") boolean hasPhoto) {

        Dev.print("---__---__---__---__---");
        Dev.print(offset);
        Dev.print(tag);
        Dev.print(rating);
        Dev.print(hasPhoto);

        return Response.ok(postRestMapper.toDTOs(postService.getPosts(offset, tag, rating, hasPhoto))).build();
    }

    /**
     * Gel ALL posts
     * @return  DTO response
     */
    @GET
    @Path("/all")
    public Response getPosts() {
        return Response.ok(postRestMapper.toDTOs(postService.getPosts())).build();
    }

    /**
     * Get specific post defined by ID.
     * @param id    id of a post given via URL
     * @return      DTO response
     */
    @GET
    @Path("/{id}")
    public Response getPost(
            @PathParam("id")
            @Min(value = 1, message = "post.id.negative")
            @ExistingPost(message = "post.id.notfound") final Long id) {
        return Response.ok(postRestMapper.toDTO(postService.getPost(id))).build();
    }

    /**
     * Gel posts of a user defined by id
     * @param userId    ID of the author of the posts
     * @return          DTO response
     */
    @GET
    @Path("/author/{userId}")
    public Response getUserPosts(
            @PathParam("userId")
            @Min(value = 1, message = "user.id.negative")
            @ExistingUser(message = "user.id.notfound") final Long userId,

            @QueryParam("offset")
            @Min(value = 0, message = "post.offset.negative") int offset) {
        return Response.ok(postRestMapper.toDTOs(postService.getUserPosts(userId, offset))).build();
    }

    @GET
    @Secured
    @Path("/rated")
    public Response getUserRatedPosts (
            @QueryParam("offset")
            @Min(value = 0, message = "post.offset.negative") int offset) {
        return Response.ok(postRestMapper.toDTOs(postService.getUserRatedPosts(offset))).build();
    }

    @GET
    @Secured
    @Path("/friends")
    public Response getUserFriendsPosts(
            @QueryParam("offset")
            @Min(value = 0, message = "post.offset.negative") int offset) {
        return Response.ok(postRestMapper.toDTOs(postService.getUserFriendsPosts(offset))).build();
    }

    /**
     * Gel posts with a tag defined by tag received via url
     * @param tag   tag criteria that is being searched
     * @return      DTO response
     */
    @GET
    @Path("/tag/{tag}")
    public Response getTagPosts(
            @PathParam("tag")
            @Size(min = 1, message = "posts.tag.size") String tag,

            @QueryParam("offset")
            @Min(value = 0, message = "post.offset.negative") int offset) {
        return Response.ok(postRestMapper.toDTOs(postService.getTagPosts(tag, offset))).build();
    }


    // -------------- POST ----------------

    /**
     * Creates a new post.
     * @param post  PostDTO (json) transferred in body.
     * @return      DTO response
     */
    @POST
    @Secured
    public Response createPost(@Valid PostDTO post) {
        return Response.ok(postService.createPost(post)).build();
    }


    /**
     * Rates the post of ID given via URL.
     * @param id    Post ID given via URL
     * @return      DTO response
     */
    @POST
    @Secured
    @Path("/{id}/rating")
    public Response ratePost(
            @PathParam("id")
            @Min(value = 1, message = "post.id.negative")
            @ExistingPost(message = "post.id.notfound") final Long id) {
        return Response.ok(ratingService.registerRating(id)).build();
    }


    // --------------- PUT ---------------

    @PUT
    @Secured
    @Path("/{id}")
    public Response modifyPost(
            @PathParam("id")
            @Min(value = 1, message = "post.id.negative")
            @ExistingPost(message = "post.id.notfound") final Long id, PostDTO post) {
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
    public Response removePost(
            @PathParam("id")
            @Min(value = 1, message = "post.id.negative")
            @ExistingPost(message = "post.id.notfound") final Long id) {
        return Response.ok(postService.removePost(id)).build();
    }


    /**
     * Rates the post of ID given via URL.
     * @param id    Post ID given via URL
     * @return      DTO response
     */
    @DELETE
    @Secured
    @Path("/{id}/rating")
    public Response unratePost(
            @PathParam("id")
            @Min(value = 1, message = "post.id.negative")
            @ExistingPost(message = "post.id.notfound") final Long id) {
        return Response.ok(ratingService.cancelRating(id)).build();
    }
}
