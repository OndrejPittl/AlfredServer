package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserEndpoint {

    @Inject
    private UserService userService;

    @Inject
    private UserRestMapper userRestMapper;



    // @TODO: initial mock data
    @GET
    @Path("/init")
    public Response init() {
        return Response.ok(userService.init()).build();
    }

    // ------------------------------

    @GET
    public Response getAllUsers() {
        return Response.ok(userRestMapper.toDTOs(userService.getAllUsers())).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") final Long id) {
        return Response.ok(userRestMapper.toDTO(userService.getUser(id))).build();
    }

    @GET
    @Path("/slug/{slug}")
    public Response getUser(@PathParam("slug") final String slug) {
        return Response.ok(userRestMapper.toDTO(userService.getUser(slug))).build();
    }

    // ------------------------------

    @POST
    public Response createUser(UserDTO user) {
        return Response.ok(userService.createUser(user)).build();
    }

    // ------------------------------

    @DELETE
    @Path("/{id}")
    public Response removeUser(@PathParam("id") final Long id) {
        return Response.ok(userService.removeUser(id)).build();
    }
}
