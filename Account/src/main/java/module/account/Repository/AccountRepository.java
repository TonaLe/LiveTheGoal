package module.account.Repository;

import module.account.Entity.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Account repository.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Find account by username account.
     *
     * @param username the username
     * @return the account
     */
//    @Query(value = "{'username':{$regex:?0}}")
    Account findAccountByUsername(String username);

    /**
     * Find account by email account.
     *
     * @param email the email
     * @return the account
     */
    Account findAccountByEmail(String email);

    /**
     * Find all account account.
     *
     * @param pageable the pageable
     * @return the account
     */
    List<Account> findAllAccount(final Pageable pageable);
}
