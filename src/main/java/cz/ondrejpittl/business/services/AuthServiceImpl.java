package cz.ondrejpittl.business.services;

import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.filters.AuthFilter;
import cz.ondrejpittl.persistence.domain.Identity;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.TokenDTO;
import cz.ondrejpittl.utils.Encryptor;
import cz.ondrejpittl.utils.TokenGenerator;
import io.undertow.util.DateUtils;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
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

    public User registerUser(String email) {
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

        user.setToken(token);
        return user;
    }

    public void rejectUser(String authString) {
        String token = authString.split(" ")[1];

        //Dev.print("Logging out user with token " + token);

        if(this.sessionUsers.containsKey(token)) {
            this.sessionUsers.remove(token);
        }
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
