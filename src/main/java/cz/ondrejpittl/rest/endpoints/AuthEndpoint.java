package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.annotations.Secured;
import cz.ondrejpittl.business.services.AuthService;
import cz.ondrejpittl.business.services.TagService;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.TagRestMapper;
import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.persistence.domain.Identity;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.TagDTO;
import cz.ondrejpittl.rest.dtos.TokenDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthEndpoint {

    @Inject
    private AuthService authService;

    @Inject
    private UserService userService;

    @Inject
    private UserRestMapper userMapper;


    @HeaderParam("Authorization")
    private String authString;



    // -------------------- GET --------------------

    @GET
    @Secured
    @Path("/me")
    public Response whoami() {
        User user = this.userService.getAuthenticatedUser();
        return Response.ok(userMapper.toDTO(user)).build();
    }


    // ------------------- POST -------------------


    @POST
    public Response authenticate(UserDTO dto) {
        String mail = dto.getEmail(),
               pwd = dto.getPassword();

        boolean authenticated = this.authService.checkUserCredentials(mail, pwd);

        // if user exists / correct credentials given
        if(authenticated) {
            User user = this.authService.registerUser(mail);
            return Response.ok(userMapper.toDTO(user)).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Secured
    @Path("/logout")
    public Response signOut() {
        this.authService.rejectUser(this.authString);
        return Response.status(Response.Status.ACCEPTED).build();
    }





}
