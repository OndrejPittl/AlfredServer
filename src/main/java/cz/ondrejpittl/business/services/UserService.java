package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.UserDTO;

import java.util.List;

public interface UserService {


    // @TODO: ---- development-only:
    public boolean init();


    // -----------------------------

    public List<User> getAllUsers();

    public User getUser(Long id);

    public User getUser(String slug);

    public User createUser(UserDTO user);

    public User removeUser(Long id);


}
