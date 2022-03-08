package com.devopsconsultants.githubapi.rest;

// import lombok.RequiredArgsConstructor;
import org.keycloak.models.KeycloakSession;
// import org.keycloak.services.managers.AppAuthManager;
// import org.keycloak.services.managers.AuthenticationManager.AuthResult;
import org.keycloak.services.resource.RealmResourceProvider;

// import javax.ws.rs.ForbiddenException;
// import javax.ws.rs.GET;
// import javax.ws.rs.NotAuthorizedException;
// import javax.ws.rs.Path;
// import javax.ws.rs.Produces;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
// import java.util.Map;

/**
 * @author Rob Coward, DevOps Consultants, @robcoward
 */
// @RequiredArgsConstructor
public class GithubApiEndpointsRealmResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;
    // private final AuthenticationManager.AuthResult auth;

    public GithubApiEndpointsRealmResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new GithubApiRestResource(session);
    }

    @Override
    public void close() {
    }

    // @GET
    // @Path("hello")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response helloAnonymous() {
    // return Response.ok(Map.of("hello",
    // session.getContext().getRealm().getName())).build();
    // }

    // @GET
    // @Path("user")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response userAuthenticated() {
    // AuthResult auth = checkAuth();
    // return Response.ok(Map.of("hello", auth.getUser().getUsername())).build();
    // }

    // @GET
    // @Path("user/teams")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response teamsAuthenticated() {
    // AuthResult auth = checkAuth();
    // return Response.ok(Map.of("hello", auth.getUser().getUsername())).build();
    // }

    // private AuthResult checkAuth() {
    // AuthResult auth = new
    // AppAuthManager.BearerTokenAuthenticator(session).authenticate();
    // // if (auth == null) {
    // // throw new NotAuthorizedException("Bearer");
    // // } else if (auth.getToken().getIssuedFor() == null ||
    // // !auth.getToken().getIssuedFor().equals("admin-cli")) {
    // // throw new ForbiddenException("Token is not properly issued for
    // admin-cli");
    // // }
    // if (auth == null) {
    // throw new NotAuthorizedException("Bearer");
    // } else if (auth.getToken().getRealmAccess() == null
    // || !auth.getToken().getRealmAccess().isUserInRole("admin")) {
    // throw new ForbiddenException("Does not have realm admin role");
    // }
    // return auth;
    // }

}
