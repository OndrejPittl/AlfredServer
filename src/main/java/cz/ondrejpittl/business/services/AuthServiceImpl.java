package cz.ondrejpittl.business.services;

import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.TokenDTO;
import cz.ondrejpittl.utils.TokenGenerator;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    private Map<String, User> sessionUsers;




    public AuthServiceImpl() {
        this.sessionUsers = new HashMap<>();
    }

    public boolean authenticate(User user, String token) {

        // invalid token received
        if(!TokenGenerator.verifyToken(token, user)) {
            return false;
        }

        User u = this.sessionUsers.get(token);
        return u != null && u.equals(user);
    }

    public TokenDTO registerUser(User user) {
        String token = TokenGenerator.generateToken(user);
        Dev.print(token);
        this.sessionUsers.put(token, user);

        Dev.print(this.sessionUsers.get(token).getEmail());
        return new TokenDTO(token);
    }

}
