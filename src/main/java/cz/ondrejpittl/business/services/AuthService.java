package cz.ondrejpittl.business.services;


import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.TokenDTO;

public interface AuthService {

    //String generateToken();

    TokenDTO registerUser(User user);

    boolean authenticate(User user, String token);

}
