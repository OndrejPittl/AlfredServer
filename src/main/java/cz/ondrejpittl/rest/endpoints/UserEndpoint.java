package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.annotations.ExistingUser;
import cz.ondrejpittl.business.annotations.Secured;
import cz.ondrejpittl.business.services.FriendshipService;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.business.validation.CreateGroup;
import cz.ondrejpittl.business.validation.ModifyGroup;
import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndpoint {

    @Inject
    private UserService userService;

    @Inject
    private UserRestMapper userRestMapper;

    @Inject
    private FriendshipService friendshipService;


    // --------------- GET ----------------

    /**
     * Get all active users.
     * @return  DTO response
     */
    @GET
    public Response getAllActiveUsers() {
        return Response.ok(userRestMapper.toDTOs(userService.getAllActiveUsers(), false)).build();
    }

    /**
     * Get all users (active/disabled).
     * @return  DTO response
     */
    @GET
    @Path("/all")
    public Response getAllUsers() {
        return Response.ok(userRestMapper.toDTOs(userService.getAllUsers(), false)).build();
    }

    /**
     * Get specific user defined by ID given via URL.
     * @param id    id of a user given via URL
     * @return      DTO response
     */
    @GET
    @Path("/{id}")
    public Response getUser(
            @PathParam("id")
            @Min(value = 1, message = "user.id.negative")
            @ExistingUser(message = "user.id.notfound") final Long id) {

        User u = userService.getUser(id);

        if(u == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(userRestMapper.toDTO(u)).build();
    }

    /*
    @GET
    @Path("/findPostsOfUsers")
    public Response test() {
        return Response.ok(userRestMapper.toDTOs(this.userService.test())).build();
    }
    */

    /**
     * Get specific user defined by slug.
     * @param slug  slug of a user given via URL
     * @return      DTO response
     */
    @GET
    @Path("/slug/{slug}")
    public Response getUser(@PathParam("slug") @Size(min = 1, message = "user.slug.length") final String slug) {
        User u = userService.getUser(slug);

        if(u == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No user " + slug + " exists.").build();
        }

        return Response.ok(userRestMapper.toDTO(u)).build();
    }

    // --------------- POST ---------------

    /**
     * Creates a new user.
     * @param user  UserDTO (json) transferred in body.
     * @return      DTO response
     */
    @POST
    public Response createUser(@Valid @ConvertGroup(from = Default.class, to = CreateGroup.class) UserDTO user) {
        return Response.ok(userService.createUser(user)).build();
    }



    /**
     * Creates a friendship with the user of ID given.
     * @return      DTO response
     */
    @POST
    @Secured
    @Path("/{id}/friendship")
    public Response createFriendship(@PathParam("id") @Min(value = 1, message = "user.id.negative") @ExistingUser(message = "user.id.notfound") final Long id) {
        return Response.ok(friendshipService.createFriendRequest(id)).build();
    }


    // --------------- PUT ---------------

    @PUT
    @Secured
    public Response modifyUser(@Valid @ConvertGroup(from = Default.class, to = ModifyGroup.class) UserDTO user) {
        return Response.ok(this.userRestMapper.toDTO(userService.modifyUser(user))).build();
    }

    /**
     * Approves a friendship with the user of ID given.
     * @return      DTO response
     */
    @PUT
    @Secured
    @Path("/{id}/friendship")
    public Response approveFriendship(@PathParam("id") @ExistingUser(message = "user.id.notfound") final Long id) {
        return Response.ok(friendshipService.approveFriendRequest(id)).build();
    }



    // -------------- DELETE --------------

    /**
     * Removes / disables the currently logged user.
     * @return      DTO response
     */
    @DELETE
    @Secured
    public Response removeUser() {
        return Response.ok(userService.disableCurrentUser()).build();
    }


    /**
     * Cancels the friendship with the user of ID given.
     * @return      DTO response
     */
    @DELETE
    @Secured
    @Path("/{id}/friendship")
    public Response cancelFriendship(@PathParam("id") @ExistingUser(message = "user.id.notfound") final Long id) {
        return Response.ok(friendshipService.cancelFriendship(id)).build();
    }







    // -----------------------------------------------------------------------------

    /*
    @GET
    @Path("/init")
    //TODO: initial mock data
    public Response init() {
        return Response.ok(userService.init()).build();
    }
    */
}
