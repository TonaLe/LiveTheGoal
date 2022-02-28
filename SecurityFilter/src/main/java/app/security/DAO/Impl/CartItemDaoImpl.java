package app.security.DAO.Impl;

import app.security.DAO.CartItemDAO;
import app.security.Entity.CartItem;
import app.security.Entity.ShoppingSession;
import app.security.Repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * The type Cart item dao.
 */
@Component
public class CartItemDaoImpl implements CartItemDAO {

    /**
     * The Cart item repository.
     */
    private final CartItemRepository cartItemRepository;

    /**
     * Instantiates a new Cart item dao.
     *
     * @param cartItemRepository the cart item repository
     */
    @Autowired
    public CartItemDaoImpl(final CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void setCartItem(final CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findCartItemBySession(final ShoppingSession shoppingSession) {
        List<CartItem> cartItems = cartItemRepository.findCartItemByShoppingSession(shoppingSession);

        if (cartItems == null) {
            return Collections.emptyList();
        }
        return cartItems;
    }
}
