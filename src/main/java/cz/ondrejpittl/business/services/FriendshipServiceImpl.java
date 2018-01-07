package cz.ondrejpittl.business.services;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.*;
import cz.ondrejpittl.persistence.repository.FriendshipRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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

        return new HashSet<User>(){{
            for(Friendship f : user.getFriendWith()) {
                if(f.isAccepted() == accepted) add(f.getFriend());
            }
        }};
    }

    @Transactional
    public User createFriendRequest(Long friendId) {
        User user = this.userService.getAuthenticatedUser();
        User friend = this.userService.getUser(friendId);
        Friendship f = new Friendship(user, friend);
        this.friendshipRepository.saveAndFlush(f);
        return null;
    }

    @Transactional
    public User approveFriendRequest(Long friendId) {
        User user = this.userService.getAuthenticatedUser();
        user.approveFriendRequest(friendId);
        return this.userRepository.save(user);
    }

    @Transactional
    public User cancelFriendship(Long friendId) {
        User user = this.userService.getAuthenticatedUser();
        Friendship f = user.cancelFriendRequest(friendId);
        this.friendshipRepository.removeById(f.getId());
        this.friendshipRepository.flush();
        return user; //return this.userRepository.save(user);
    }
}
