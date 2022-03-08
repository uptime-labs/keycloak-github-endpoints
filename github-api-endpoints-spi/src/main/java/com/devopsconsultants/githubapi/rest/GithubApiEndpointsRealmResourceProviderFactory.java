package com.devopsconsultants.githubapi.rest;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

/**
 * @author Rob Coward, DevOps Consultants, @robcoward
 */
public class GithubApiEndpointsRealmResourceProviderFactory implements RealmResourceProviderFactory {

    public static final String PROVIDER_ID = "github-api";

    @Override
    public RealmResourceProvider create(KeycloakSession keycloakSession) {
        return new GithubApiEndpointsRealmResourceProvider(keycloakSession);
    }

    @Override
    public void init(Scope scope) {
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
