package app.security.Repository;

import app.security.Entity.Account;
import app.security.Entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Shopping session repository.
 */
@Repository
public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Integer> {

    /**
     * Find shopping session by account shopping session.
     *
     * @param account the account
     * @return the shopping session
     */
    ShoppingSession findShoppingSessionByAccount(final Account account);
}
