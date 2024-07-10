package jg.chip.adapter;

import jg.chip.model.Account;
import jg.chip.model.dto.AccountResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountAdapter implements Adapter<Account, AccountResponseDto> {
    @Override
    public AccountResponseDto adapt(Account account, Long currentAccountId) {
        final long accountId = account.getId();
        return new AccountResponseDto(
            accountId,
            account.getUsername(),
            account.getPlayerId(),
            account.isEnabled(),
            accountId == currentAccountId
        );
    }
}
