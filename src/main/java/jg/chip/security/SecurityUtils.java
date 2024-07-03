package jg.chip.security;

import jg.chip.model.Account;
import jg.chip.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Log4j2
public class SecurityUtils {

    final AccountRepository accountRepository;

    public Long getCurrentAccountId() {
        final Account currentAccount = getCurrentAccount();
        return currentAccount == null ? null : currentAccount.getId();
    }

    public Account getCurrentAccount() {
        return accountRepository.findByUsername(getCurrentUsername());
    }

    /**
     * Get the login of the current account.
     *
     * @return the login of the current account.
     */
    public String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("no authentication in security context found");
            return null;
        }

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String principal) {
            username = principal;
        }

        log.debug("found username '{}' in security context", username);

        return username;
    }
}
