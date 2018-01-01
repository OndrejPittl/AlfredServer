package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.UserDTO;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User getUser(Long id);

    public User createUser(UserDTO user);
}
