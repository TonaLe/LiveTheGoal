package app.security.Service;

import app.security.DTO.CartDto;

import java.util.List;

/**
 * The interface Cart service.
 */
public interface CartService {

    /**
     * Sets cart.
     *
     * @param cartDto the cart dto
     * @return the cart
     */
    Integer setCart(final CartDto cartDto);

    /**
     * Find carts by username list.
     *
     * @param username the username
     * @return the list
     */
    List<CartDto> findCartsByUsername(final String username);

    /**
     * Update cart.
     *
     * @param cartDto the cart dto
     * @param id      the id
     */
    void updateCart(final CartDto cartDto, final Integer id);

    /**
     * Delete cart.
     *
     * @param id the id
     */
    void deleteCart(final Integer id);
}
