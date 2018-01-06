package cz.ondrejpittl.business.services;

import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.Identity;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.TokenDTO;
import cz.ondrejpittl.utils.Encryptor;
import cz.ondrejpittl.utils.TokenGenerator;
import io.undertow.util.DateUtils;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    private Map<String, Identity> sessionUsers;





    public AuthServiceImpl() {
        this.sessionUsers = new HashMap<>();
    }

    public User authenticate(String token) {


        if(this.sessionUsers.containsKey(token)) {
            Identity identity = this.sessionUsers.get(token);
            User user = this.userService.getUser(identity.getUserId());
            return user;
        }

        return null;
    }

    /*
    public boolean authenticate(User user, String token) {

        // invalid token received
        if(!TokenGenerator.verifyToken(token, user)) {
            return false;
        }

        User u = this.sessionUsers.get(token);
        return u != null && u.equals(user);
    }
    */

    public TokenDTO registerUser(String email) {
        User user = this.userService.getUserByEmail(email);
        String token = TokenGenerator.generateToken(user);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Date expirationDate = cal.getTime();

        Identity identity = new Identity(
            user.getId(),
            token,
            expirationDate
        );

        this.sessionUsers.put(token, identity);
        return new TokenDTO(token);
    }

    public boolean checkUserCredentials(String mail, String pwd) {
        User u = this.userRepository.findByEmailLike(mail);

        if(u == null) {
            return false;
        }

        String hashedPwd = u.getPassword();
        return Encryptor.verify(pwd, hashedPwd);
    }

    public Identity getIdentity(String token) {
        if(!this.sessionUsers.containsKey(token)) {
            return null;
        }

        return this.sessionUsers.get(token);
    }
}
