package com.devopsconsultants.githubapi.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.Map;

public class GithubApiRestResource {

    private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

    public GithubApiRestResource(KeycloakSession session) {
        this.session = session;
        this.auth = new AppAuthManager.BearerTokenAuthenticator(session).authenticate();
    }

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloAnonymous() {
        return Response.ok(Map.of("hello", session.getContext().getRealm().getName())).build();
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userAuthenticated() {
        checkRealmAdmin();
        return Response.ok(Map.of("hello", auth.getUser().getUsername())).build();
    }

    @GET
    @Path("user/teams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response teamsAuthenticated() {
        checkRealmAdmin();
        return Response.ok(Map.of("hello", auth.getUser().getUsername())).build();
    }

    private void checkRealmAdmin() {
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        } else if (auth.getToken().getRealmAccess() == null
                || !auth.getToken().getRealmAccess().isUserInRole("admin")) {
            throw new ForbiddenException("Does not have realm admin role");
        }
    }
}
