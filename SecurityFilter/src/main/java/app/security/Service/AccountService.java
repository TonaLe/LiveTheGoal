package app.security.Service;


import app.security.DTO.AccountDto;
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
    void setAccount(final AccountDto account);
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
     * @param limit  the limit
     * @param offset the offset
     * @return the list account
     */
    List<AccountDto> getListAccount(final int limit, final int offset);

    /**
     * Update account info.
     *
     * @param username   the username
     * @param accountDto the account dto
     */
    void updateAccountInfo(final String username, final AccountDto accountDto);

    /**
     * Delete account.
     *
     * @param username the username
     */
    void deleteAccount(final String username);
}
