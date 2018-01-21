package cz.ondrejpittl.business.services;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.business.validation.ValidationMessages;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.persistence.domain.*;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.UserDTO;
import cz.ondrejpittl.utils.Encryptor;
import cz.ondrejpittl.utils.IOManager;
import cz.ondrejpittl.utils.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.swing.*;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    @AuthenticatedUser
    Identity authenticatedUser;

    @Inject
    UserRepository userRepository;

    @Inject
    UserRestMapper userMapper;

    @Inject
    TagService tagService;

    @Inject
    AuthService authService;

    @Inject
    FriendshipService friendshipService;



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByDisabledEqualOrderByFirstNameAsc(false); // disabled == false == 0
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }

    public User getActiveUser(Long id) {
        return userRepository.findByIdAndDisabledEqual(id, false);
    }

    public User getUser(String slug) {
        return userRepository.findBySlug(slug);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailLike(email);
    }

    @Transactional
    public User createUser(UserDTO dto) {
        User user = userMapper.fromDTO(dto);
        user.setSlug(this.buildUserSlug(user));
        user.setPassword(Encryptor.bcrypt(user.getPassword()));

        Dev.print(user.getPhoto());

        if(user.getPhoto() != null && user.getPhoto().length() > 0) {
            user.setPhoto(IOManager.saveFile(user.getPhoto()));
        }

        return userRepository.saveAndFlush(user);
    }

    public boolean checkEmailAvailability(String email) {
        Long c = this.userRepository.countUsersByEmail(email);
        return c <= 0;
    }


    private String buildUserSlug(User u) {
        String candidate = (
            StringUtils.stripAccents(u.getFirstName()) + "-" + StringUtils.stripAccents(u.getLastName())
        ).replace(" ", "-").toLowerCase();

        // if taken
        while (this.userRepository.countUsersBySlug(candidate) > 0) {
            candidate += "-" + ((int)(Math.random() * 99999 + 1));
        }

        return candidate;
    }

    public User disableCurrentUser() {
        Long id = this.authenticatedUser.getUserId();
        return this.disableUser(id);
    }

    @Transactional
    public User disableUser(Long id) {
        User u = this.getUser(id);
        u.setDisabled(true);
        return this.userRepository.save(u);
    }

    @Transactional
    public User modifyUser(UserDTO user) {
        Long uID = this.authenticatedUser.getUserId();
        User u = this.getUser(uID);

        // optional
        if(user.getPassword() != null) {
            u.setPassword(Encryptor.bcrypt(user.getPassword()));
        }

        // required –> always set
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setEmail(user.getEmail());

        // optional
        if(user.getPhoto() != null) {
            u.setPhoto(IOManager.saveFile(user.getPhoto()));
        }

        this.userRepository.saveAndFlush(u);
        return this.getUser(uID);
    }

    public boolean checkUserExists(String email, String hashedPwd) {
        Long count = this.userRepository.countUsers(email, hashedPwd);
        return count == 1;
    }

    public User getAuthenticatedUser() {
        return this.getUser(this.authenticatedUser.getUserId());
    }

    public boolean checkUserExists(Long id) {
        return this.userRepository.countUsers(id) == 1;
    }
}
