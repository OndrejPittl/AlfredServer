package cz.ondrejpittl.business.services;

import cz.ondrejpittl.persistence.domain.Friendship;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.rest.dtos.UserDTO;

import java.util.List;
import java.util.Set;

public interface FriendshipService {

    Set<User> getFriends();

    Set<User> getFriendRequests();

    User createFriendRequest(Long friendId);

    User approveFriendRequest(Long friendId);

    User cancelFriendship(Long friendId);



}
