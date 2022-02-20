package app.security.Service;


import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

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
//
//    /**
//     * Gets account by username.
//     *
//     * @param username the username
//     * @return the account by username
//     */
//    Account getAccountByUsername(final String username);

    /**
     * Load account by username account dto.
     *
     * @param username the username
     * @return the account dto
     */
    AccountDto loadAccountByUsername(final String username);

    /**
     * Init account list.
     *
     * @param listAccount the list account
     * @param key         the key
     */
    void initAccountList(final List<AccountDto> listAccount, final String key);

    /**
     * Gets list account.
     *
     * @return the list account
     */
    List<AccountDto> getListAccount();
}
