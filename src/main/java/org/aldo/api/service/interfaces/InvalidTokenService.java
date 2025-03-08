package org.aldo.api.service.interfaces;

public interface InvalidTokenService {
    void invalidationToken(String jwt);
    Boolean isTokenInvalidated(String jwt);
}
