package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.business.annotations.Secured;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.TokenDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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



    // --------------- GET ----------------

    /**
     * Get all active users.
     * @return  DTO response
     */
    @GET
    public Response getAllActiveUsers() {
        return Response.ok(userRestMapper.toDTOs(userService.getAllActiveUsers())).build();
    }

    /**
     * Get all users (active/disabled).
     * @return  DTO response
     */
    @GET
    @Path("/all")
    public Response getAllUsers() {
        return Response.ok(userRestMapper.toDTOs(userService.getAllUsers())).build();
    }

    /**
     * Get specific user defined by ID.
     * @param id    id of a user given via URL
     * @return      DTO response
     */
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") final Long id) {
        return Response.ok(userRestMapper.toDTO(userService.getUser(id))).build();
    }

    /**
     * Get specific user defined by slug.
     * @param slug  slug of a user given via URL
     * @return      DTO response
     */
    @GET
    @Path("/slug/{slug}")
    public Response getUser(@PathParam("slug") final String slug) {
        return Response.ok(userRestMapper.toDTO(userService.getUser(slug))).build();
    }

    // --------------- POST ---------------

    /**
     * Creates a new user.
     * @param user  UserDTO (json) transferred in body.
     * @return      DTO response
     */
    @POST
    public Response createUser(UserDTO user) {
        return Response.ok(userService.createUser(user)).build();
    }


    // -------------- PATCH ---------------

    @PUT
    @Secured
    public Response modifyUser(UserDTO user) {
        return Response.ok(this.userRestMapper.toDTO(userService.modifyUser(user))).build();
    }



    // -------------- DELETE --------------

    /**
     * Removes / disables the currently logged user.
     * @return      DTO response
     */
    @DELETE
    @Secured
    //@Path("/{id}") public Response removeUser(@PathParam("id") final Long id) {
    public Response removeUser() {
        return Response.ok(userService.disableCurrentUser()).build();
    }









    // -----------------------------------------------------------------------------
    @GET
    @Path("/init")
    //TODO: initial mock data
    public Response init() {
        return Response.ok(userService.init()).build();
    }
}
