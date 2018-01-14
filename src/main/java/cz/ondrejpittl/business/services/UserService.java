package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Friendship;
import cz.ondrejpittl.persistence.domain.Identity;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.UserDTO;

import java.util.List;

public interface UserService {


    // @TODO: ---- development-only:
    // boolean init();


    // -----------------------------

    List<User> getAllUsers();

    List<User> getAllActiveUsers();

    User getUser(Long id);

    User getActiveUser(Long id);

    User getUser(String slug);

    User getUserByEmail(String email);

    User createUser(UserDTO dto);

    User disableCurrentUser();

    User disableUser(Long id);

    User modifyUser(UserDTO user);

    boolean checkUserExists(String email, String hashedPwd);

    User getAuthenticatedUser();

    boolean checkEmailAvailability(String email);

    boolean checkUserExists(Long id);

    List<User> test();
}
