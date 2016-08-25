package net.iegreen.web.context;

import net.iegreen.domain.shared.security.AndailyUserDetails;
import net.iegreen.domain.shared.security.SecurityHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Shengzhao Li
 */
public class SpringSecurityHolder implements SecurityHolder {

    @Override
    public AndailyUserDetails userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AndailyUserDetails) {
            return (AndailyUserDetails) principal;
        }
        return null;
    }
}