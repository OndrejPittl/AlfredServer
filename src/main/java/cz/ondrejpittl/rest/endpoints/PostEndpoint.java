package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.annotations.ExistingPost;
import cz.ondrejpittl.business.annotations.ExistingUser;
import cz.ondrejpittl.business.annotations.Secured;
import cz.ondrejpittl.business.cfg.Config;
import cz.ondrejpittl.business.services.CommentService;
import cz.ondrejpittl.business.services.PostService;
import cz.ondrejpittl.business.services.RatingService;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.CommentRestMapper;
import cz.ondrejpittl.mappers.PostRestMapper;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.rest.dtos.PostDTO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.Map;

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
    private UserService userService;


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

        return Response.ok(postRestMapper.toDTOs(postService.getPosts(offset, tag, rating, hasPhoto), false)).build();
    }

    /**
     * Gel ALL posts
     * @return  DTO response
     */
    @GET
    @Path("/all")
    public Response getPosts() {
        return Response.ok(postRestMapper.toDTOs(postService.getPosts(), false)).build();
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

        Post p = postService.getPost(id);
        PostDTO dto = postRestMapper.toDTO(p);
        return Response.ok(dto).build();
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
        return Response.ok(postRestMapper.toDTOs(postService.getUserPosts(userId, offset), false)).build();
    }

    @GET
    @Secured
    @Path("/rated")
    public Response getUserRatedPosts (
            @QueryParam("offset")
            @Min(value = 0, message = "post.offset.negative") int offset) {
        return Response.ok(postRestMapper.toDTOs(postService.getUserRatedPosts(offset), false)).build();
    }

    @GET
    @Secured
    @Path("/friends")
    public Response getUserFriendsPosts(
            @QueryParam("offset")
            @Min(value = 0, message = "post.offset.negative") int offset) {
        return Response.ok(postRestMapper.toDTOs(postService.getUserFriendsPosts(offset), false)).build();
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
        return Response.ok(postRestMapper.toDTOs(postService.getTagPosts(tag, offset), false)).build();
    }


    // -------------- POST ----------------

    /**
     * Creates a new post.
     * @param post  PostDTO (json) transferred in body.
     * @return      DTO response
     */
    @POST
    @Secured    //@Valid
    public Response createPost(PostDTO post) {
        //Dev.print("Create Post Endpoint reached!");
        //Dev.printObject(post);
        List<Post> posts = postService.createPost(post);
        return Response.ok(this.postRestMapper.toDTOs(posts)).build();
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

        Post post = ratingService.createRating(id);
        PostDTO dto = this.postRestMapper.toDTO(post);

        return Response.ok(dto).build();
    }


    /*

    @POST
    @Path("/{id}/fileupload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPostImage(
            @PathParam("id") final Long id,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {


        // image location
        String uploadedFileLocation = Config.IMAGES_DESTINATION + fileDetail.getFileName();
        //System.out.println(uploadedFileLocation);


        // save it
        File objFile = new File(uploadedFileLocation);
        if(objFile.exists()) {
            objFile.delete();
        }

        saveToFile(uploadedInputStream, uploadedFileLocation);

        String output = "File saved to: " + uploadedFileLocation;

        //Dev.print(output);

        //return Response.status(200).entity(output).build();
        return Response.ok(new PostDTO()).build();

    }
    private void saveToFile(InputStream uploadedInputStream,
                            String uploadedFileLocation) {

        try {
            OutputStream out = null;
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    @Path("/uploadfile")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(MultipartFormDataInput input) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        return Response.ok().build();
    }

*/




    // --------------- PUT ---------------

    @PUT
    @Secured
    @Path("/{id}")
    public Response modifyPost(
            @PathParam("id")
            @Min(value = 1, message = "post.id.negative")
            @ExistingPost(message = "post.id.notfound") final Long id, PostDTO post) {
        //Dev.print("POST PUT: Endpoint reached.");
        return Response.ok(this.postRestMapper.toDTO(postService.modifyPost(id, post))).build();
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

        List<Post> posts = postService.removePost(id);
        List<PostDTO> dtos = this.postRestMapper.toDTOs(posts);
        return Response.ok(dtos).build();
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

        Post post = ratingService.cancelRating(id);
        PostDTO dto = this.postRestMapper.toDTO(post);
        //dto.setUserRated(false);

        return Response.ok(dto).build();
    }
}
