package app.security.DAO;

import app.security.Entity.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


/**
 * The interface Account dao.
 */
public interface AccountDAO {
    /**
     * Load userby name user details.
     *
     * @param user the user
     * @return the user details
     */
    UserDetails loadUserbyName(String user);

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
