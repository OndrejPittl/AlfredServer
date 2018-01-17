package cz.ondrejpittl.business.services;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.*;
import cz.ondrejpittl.persistence.repository.FriendshipRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
public class FriendshipServiceImpl implements FriendshipService {

    @Inject
    @AuthenticatedUser
    Identity authenticatedUser;

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Inject
    FriendshipRepository friendshipRepository;



    public Set<User> getFriends() {
        return this.collectFriendships(true);
    }


    public Set<User> getFriendRequests() {
        return this.collectFriendships(false);
    }

    private Set<User> collectFriendships(boolean accepted) {
        User user = this.userService.getAuthenticatedUser();

        Dev.print("Getting friend[ships|requests] of:");
        Dev.printObject(user);

        HashSet<User> friends = new HashSet<>();
        for(Friendship f : user.getFriendWith()) {
            if(f.isAccepted() == accepted && !f.getFriend().isDisabled()) {
                friends.add(f.getFriend());
            }
        }

        return friends.size() > 0 ? friends : null;
    }

    @Transactional
    public User createFriendRequest(Long friendId) {
        User user = this.userService.getAuthenticatedUser();

        if(user.getId().equals(friendId)) {
            throw new WebApplicationException("Denied: You can't be your friend!", Response.Status.CONFLICT);
        }

        User friend = this.userService.getActiveUser(friendId);

        if(friend == null) {
            throw new WebApplicationException("User ID " + friendId + " not found.", Response.Status.NOT_FOUND);
        }

        Friendship f = new Friendship(user, friend);
        this.friendshipRepository.saveAndFlush(f);
        return this.userRepository.findById(friendId);
    }

    @Transactional
    public User approveFriendRequest(Long friendId) {
        User user = this.userService.getAuthenticatedUser();
        boolean result = user.approveFriendRequest(friendId);

        if(!result) {
            throw new WebApplicationException("Friend request not found.", Response.Status.NOT_FOUND);
        }

        this.userRepository.saveAndFlush(user);
        return this.userRepository.findById(friendId);
    }

    @Transactional
    public User cancelFriendship(Long friendId) {
        User user = this.userService.getAuthenticatedUser();
        Friendship f = user.cancelFriendRequest(friendId);

        if(f == null) {
            throw new WebApplicationException("Friendship/friend request not found.", Response.Status.NOT_FOUND);
        }

        this.friendshipRepository.removeById(f.getId());
        this.friendshipRepository.flush();
        return this.userRepository.findById(friendId);
    }
}
