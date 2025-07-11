package org.aldo.api.logging;

import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.aldo.api.service.Implementations.UserDetailsServiceImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
@NonNullApi
public class AuditorAwareImpl implements AuditorAware<String> {
    private final UserDetailsServiceImpl userDetailsService;
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.of(userDetailsService.getCurrentUserEmail());
        } catch (Exception e) {
            return Optional.of("system");
        }
    }
}
