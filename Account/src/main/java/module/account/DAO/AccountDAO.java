package module.account.DAO;


import module.account.Entity.Account;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Account dao.
 */
public interface AccountDAO {

    /**
     * Sets account.
     *
     * @param account the account
     */
    void setAccount(final Account account);

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user by username
     */
    Account getUserByUsername(final String username);

    /**
     * Gets user by email.
     *
     * @param email the email
     * @return the user by email
     */
    Account getUserByEmail(final String email);

    /**
     * Find all account account.
     *
     * @param pageable the pageable
     * @return the account
     */
    List<Account> findAllAccount(final Pageable pageable);
}
