package cz.ondrejpittl.rest.endpoints;

import cz.ondrejpittl.business.services.AuthService;
import cz.ondrejpittl.business.services.TagService;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.TagRestMapper;
import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.TagDTO;
import cz.ondrejpittl.rest.dtos.TokenDTO;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/auth")
@Produces("application/json")
@Consumes("application/json")
public class AuthEndpoint {

    @Inject
    private AuthService authService;

    @Inject
    private UserService userService;

    @Inject
    private UserRestMapper userMapper;


    @POST
    public Response authenticate(UserDTO user) {
        String mail = user.getEmail(),
               pwd = user.getPassword();

        // if user exists / correct credentials given
        if(this.authService.checkUserCredentials(mail, pwd)) {
            TokenDTO token = this.authService.registerUser(mail);
            return Response.ok(token).build();
        }

        return Response.ok(new TokenDTO(null)).build();
    }






}
