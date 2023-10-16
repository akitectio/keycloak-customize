package dev.ducduy.userstorageproviderspi;


import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

public class CustomUserStorageProvider implements UserLookupProvider, CredentialInputValidator, UserStorageProvider {
    @Override
    public boolean supportsCredentialType(String credentialType) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }
}