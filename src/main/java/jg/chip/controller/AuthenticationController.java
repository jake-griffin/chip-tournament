package jg.chip.controller;

import jakarta.validation.Valid;
import jg.chip.model.Account;
import jg.chip.model.dto.LoginDto;
import jg.chip.model.dto.LoginResponseDto;
import jg.chip.model.dto.RegisterAccountDto;
import jg.chip.repository.AccountRepository;
import jg.chip.security.MyUserDetails;
import jg.chip.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller to authenticate users.
 */
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountRepository accountRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterAccountDto newAccount) {
        if (accountRepository.findByUsername(newAccount.username()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account already exists.");
        } else {
            final Account account = new Account();
            account.setUsername(newAccount.username());
            account.setPassword(new BCryptPasswordEncoder().encode(newAccount.password()));
            account.setRole("ROLE_USER");
            account.setEnabled(true);
            accountRepository.save(account);
        }
    }

    @PostMapping(path = "/login")
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        Account account = ((MyUserDetails) authentication.getPrincipal()).getAccount();

        return new LoginResponseDto(account.getId(), jwt);
    }
}
