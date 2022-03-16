package com.devopsconsultants.githubapi.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RoleModel;
import org.keycloak.models.RoleMapperModel;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        }
        String name;
        name = new StringBuilder()
            .append(auth.getUser().getFirstName())
            .append(" ")
            .append(auth.getUser().getLastName())
            .toString();
        return Response.ok(Map.of("login", auth.getUser().getUsername(),
                                    "id", auth.getUser().getId(),
                                    "email", auth.getUser().getEmail(),
                                    "name", name
        )).build();
    }

    @GET
    @Path("user/teams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response teamsAuthenticated() {
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        }

        // Get any directly assigned roles
        List<Map<String,Object>> rolesList = auth.getUser().getRoleMappingsStream()
                                                .map( p -> Map.of("name", p.getName(), 
                                                                   "slug", p.getName(), 
                                                                   "organization", Map.of("login", session.getContext().getRealm().getName()) ))
                                                .collect(Collectors.toList());

        // Also check for roles assigned to groups
        List<String> groupRoles = auth.getUser().getGroupsStream()
                                        .map( g -> g.getRoleMappingsStream()
                                                       .map( p -> p.getName() )
                                                       .collect(Collectors.toList()) )
                                        .flatMap(Collection::stream)
                                        .collect(Collectors.toList()); 
        List<Map<String, Object>> groupRolesList = groupRoles.stream().map( p -> Map.of( "name", p,
                                                                                         "slug", p,
                                                                                         "organization", Map.of("login", session.getContext().getRealm().getName()) ))
                                                                      .collect(Collectors.toList());

        return Response.ok(Stream.of(rolesList, groupRolesList).flatMap(Collection::stream).collect(Collectors.toList()) ).build();
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
