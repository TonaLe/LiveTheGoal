package app.security.DAO.Impl;

import app.security.DAO.ShoppingSessionDAO;
import app.security.Entity.Account;
import app.security.Entity.ShoppingSession;
import app.security.Repository.ShoppingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Shopping session dao.
 */
@Component
public class ShoppingSessionDaoImpl implements ShoppingSessionDAO {

    /**
     * The Shopping session repository.
     */
    private final ShoppingSessionRepository shoppingSessionRepository;

    /**
     * Instantiates a new Shopping session dao.
     *
     * @param shoppingSessionRepository the shopping session repository
     */
    @Autowired
    public ShoppingSessionDaoImpl(final ShoppingSessionRepository shoppingSessionRepository) {
        this.shoppingSessionRepository = shoppingSessionRepository;
    }

    @Override
    public void setShoppingSession(final ShoppingSession shoppingSession) {
        shoppingSessionRepository.save(shoppingSession);
    }

    @Override
    public ShoppingSession getShoppingSessionByAccount(final Account account) {
        return shoppingSessionRepository.findShoppingSessionByAccount(account);
    }
}
