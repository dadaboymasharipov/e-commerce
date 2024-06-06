package com.example.appecommerce.component;

import com.example.appecommerce.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class AuditingAwareImpl implements AuditorAware<UUID> {
    /**
     * This Method return id of the user after they created or updated something
     *
     * @return Optional with UUID or empty optional
     */
    @Override
    public Optional<UUID> getCurrentAuditor() {
        //Getting authentication from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //Checking the credentials
        if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {

            //Get current user from authentication
            User user = (User) authentication.getPrincipal();

            //return user inside of optional
            return Optional.of(user.getId());
        }

        //Else return empty optional
        return Optional.empty();
    }
}
