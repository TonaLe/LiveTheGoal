package app.security.Repository;

import app.security.Entity.CartItem;
import app.security.Entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Cart item repository.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

    /**
     * Find cart item by shopping session list.
     *
     * @param shoppingSession the shopping session
     * @return the list
     */
    List<CartItem> findCartItemByShoppingSession(final ShoppingSession shoppingSession);

    /**
     * Find cart item by id cart item.
     *
     * @param id the id
     * @return the cart item
     */
    CartItem findCartItemById(final int id);
}
