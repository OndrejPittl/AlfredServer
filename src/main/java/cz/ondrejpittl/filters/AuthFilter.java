package cz.ondrejpittl.filters;

import cz.ondrejpittl.business.annotations.AuthenticatedUser;
import cz.ondrejpittl.business.annotations.Secured;
import cz.ondrejpittl.business.cfg.Config;
import cz.ondrejpittl.business.services.AuthService;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    private static final String AUTH_SCHEME = "Bearer";

    @Inject
    private AuthService authService;

    @Inject
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent;



    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {

        // request –> auth header
        String authHeader = reqContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // validate auth header
        if (!validateAuthHeader(authHeader)) {
            abortUnauthorized(reqContext);
            return;
        }

        // auth header –> token
        String token = authHeader.substring(AuthFilter.AUTH_SCHEME.length()).trim();

        try {

            // validate token
            validateToken(token);

            // --- user auth succeeded ---
            this.userAuthenticatedEvent.fire(token);

        } catch (Exception e) {
            abortUnauthorized(reqContext);
        }
    }

    /**
     * Validates Authorization header format.
     */
    private boolean validateAuthHeader(String authHeader) {
        return authHeader != null && authHeader.toLowerCase().startsWith(AuthFilter.AUTH_SCHEME.toLowerCase() + " ");
    }

    /**
     * Aborts with 401 Unauthorized status.
     */
    private void abortUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
            Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE,AuthFilter.AUTH_SCHEME + " realm=\"" + Config.APP_REALM + "\"")
                .build());
    }

    /**
     * Validates token.
     */
    private void validateToken(String token) throws Exception {
        if(this.authService.authenticate(token) == null) {
            throw new Exception();
        }
    }
}