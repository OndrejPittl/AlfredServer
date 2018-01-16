package cz.ondrejpittl.business.services;


import cz.ondrejpittl.persistence.domain.Identity;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.TokenDTO;

public interface AuthService {

    User registerUser(String email);

    void rejectUser(String authString);

    //boolean authenticate(User user, String token);

    User authenticate(String token);

    boolean checkUserCredentials(String mail, String pwd);

    Identity getIdentity(String token);


}
