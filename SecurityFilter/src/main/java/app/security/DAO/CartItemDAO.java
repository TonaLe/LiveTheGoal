package app.security.DAO;

import app.security.Entity.CartItem;
import app.security.Entity.ShoppingSession;

import java.util.List;

/**
 * The interface Cart item dao.
 */
public interface CartItemDAO {
    /**
     * Sets cart item.
     *
     * @param cartItem the cart item
     */
    void setCartItem(final CartItem cartItem);

    /**
     * Find cart item by session list.
     *
     * @param shoppingSession the shopping session
     * @return the list
     */
    List<CartItem> findCartItemBySession(final ShoppingSession shoppingSession);
}
