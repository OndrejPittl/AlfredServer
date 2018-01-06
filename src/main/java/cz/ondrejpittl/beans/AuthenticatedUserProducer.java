package cz.ondrejpittl.beans;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.business.services.AuthService;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.persistence.domain.Identity;
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
    private Identity authenticatedUser;

    @Inject
    private AuthService authService;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String token) {
        this.authenticatedUser = findUser(token);
    }

    private Identity findUser(String token) {
        return this.authService.getIdentity(token);
    }
}
