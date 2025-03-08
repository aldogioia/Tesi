package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.InvalidTokenDao;
import org.aldo.api.data.entities.InvalidToken;
import org.aldo.api.handler.JwtHandler;
import org.aldo.api.service.interfaces.InvalidTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidTokenServiceImpl implements InvalidTokenService {
    private final InvalidTokenDao invalidTokenDao;
    private final JwtHandler jwtHandler;
    @Override
    public void invalidationToken(String jwt) {
        try {
            InvalidToken invalidToken = new InvalidToken();
            invalidToken.setToken(jwt);
            invalidToken.setExpirationDate(jwtHandler.getExpirationDateFromToken(jwt));
            invalidTokenDao.save(invalidToken);
        }
        catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }

    @Override
    public Boolean isTokenInvalidated(String jwt) {
        return invalidTokenDao.existsByToken(jwt);
    }
}
