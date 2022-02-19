package app.security.Service;


import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The interface Account service.
 */
public interface AccountService extends UserDetailsService {

    /**
     * Sets account.
     *
     * @param account the account
     */
    void setAccount(final Account account);

    /**
     * Gets account by username.
     *
     * @param username the username
     * @return the account by username
     */
    Account getAccountByUsername(final String username);

    /**
     * Load account by username account dto.
     *
     * @param username the username
     * @return the account dto
     */
    AccountDto loadAccountByUsername(final String username);

    /**
     * Init account event.
     *
     * @param accountDto the account dto
     */
    void initAccountEvent(final AccountDto accountDto);
}
