package cz.ondrejpittl.beans;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.persistence.domain.User;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    private User authenticatedUser;

    @Inject
    private UserService userService;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
        this.authenticatedUser = findUser(username);
    }

    private User findUser(String slug) {
        return this.userService.getUser(slug);
    }
}
