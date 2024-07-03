package jg.chip.security;

import jg.chip.model.Account;
import jg.chip.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("Could not find account " + username);
        }

        if (!account.isEnabled()) {
            throw new AccountNotEnabledException("Account " + username + " is not enabled");
        }

        return new MyUserDetails(account);
    }

}
