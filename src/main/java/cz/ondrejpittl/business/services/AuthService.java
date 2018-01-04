package cz.ondrejpittl.business.services;


import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.TokenDTO;

public interface AuthService {

    TokenDTO registerUser(String email);

    //boolean authenticate(User user, String token);

    User authenticate(String token);

    boolean checkUserCredentials(String mail, String pwd);

}
