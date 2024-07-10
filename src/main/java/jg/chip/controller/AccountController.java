package jg.chip.controller;

import jg.chip.adapter.AccountAdapter;
import jg.chip.exception.BadRequestException;
import jg.chip.exception.ResourceNotFoundException;
import jg.chip.model.Account;
import jg.chip.model.dto.AccountResponseDto;
import jg.chip.repository.AccountRepository;
import jg.chip.repository.PlayerRepository;
import jg.chip.security.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
@Secured("ROLE_ADMIN")
@RequestMapping("/accounts")
public class AccountController {
    private final PlayerRepository playerRepository;
    private AccountRepository accountRepository;
    private AccountAdapter accountAdapter;
    private SecurityUtils securityUtils;

    @GetMapping
    public List<AccountResponseDto> getActiveAccounts() {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        return accountRepository.findAllEnabled()
            .stream()
            .map(account -> accountAdapter.adapt(account, currentAccountId))
            .toList();
    }

    @GetMapping("/all")
    public List<AccountResponseDto> getAllAccounts() {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        return accountRepository.findAll()
            .stream()
            .map(account -> accountAdapter.adapt(account, currentAccountId))
            .toList();
    }

    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(@PathVariable Long id) {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        return accountAdapter.adapt(
            accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account does not exist with id: " + id)),
            currentAccountId
        );
    }

    @PutMapping("/{accountId}/assignPlayer/{playerId}")
    @Secured("ROLE_ADMIN")
    public AccountResponseDto assignPlayerToAccount(
        @PathVariable Long accountId,
        @PathVariable Long playerId
    ) {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        final Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account does not exist with id: " + accountId));

        playerRepository.findById(playerId)
            .orElseThrow(() -> new ResourceNotFoundException("Player does not exist with id: " + playerId));

        account.setPlayerId(playerId);

        return accountAdapter.adapt(accountRepository.save(account), currentAccountId);
    }

    @PutMapping("/{id}/disable")
    @Secured("ROLE_ADMIN")
    public AccountResponseDto disableAccount(
        @PathVariable Long id
    ) {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        if (currentAccountId == id) {
            throw new BadRequestException("Cannot disable currently logged in account");
        }

        final Account account = accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account does not exist with id: " + id));

        account.setEnabled(false);

        return accountAdapter.adapt(accountRepository.save(account), currentAccountId);
    }

    @PutMapping("/{id}/enable")
    @Secured("ROLE_ADMIN")
    public AccountResponseDto enableAccount(
        @PathVariable Long id
    ) {
        final long currentAccountId = securityUtils.getCurrentAccountId();

        final Account account = accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account does not exist with id: " + id));

        account.setEnabled(true);

        return accountAdapter.adapt(accountRepository.save(account), currentAccountId);
    }
}
