package app.security.DAO;

import app.security.Entity.Account;
import app.security.Entity.ShoppingSession;

/**
 * The interface Shopping session dao.
 */
public interface ShoppingSessionDAO {
    /**
     * Sets shopping session.
     *
     * @param shoppingSession the shopping session
     */
    void setShoppingSession(final ShoppingSession shoppingSession);

    /**
     * Gets shopping session by account.
     *
     * @param account the account
     * @return the shopping session by account
     */
    ShoppingSession getShoppingSessionByAccount(final Account account);
}
