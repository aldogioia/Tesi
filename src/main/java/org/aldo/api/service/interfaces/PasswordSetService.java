package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.RequestSetPasswordDto;

public interface PasswordSetService {
    void initiateSetPassword(String email);

    void setPassword(RequestSetPasswordDto requestSetPasswordDto);
}
