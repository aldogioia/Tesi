package org.aldo.api.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.aldo.api.data.dto.AccessDto;

public interface AuthService {
    AccessDto checkFirstAccess(String email);
    String login(String email);
    void logout(HttpServletRequest request);
}
