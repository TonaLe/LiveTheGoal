package app.security.Service.Impl;

import app.security.DAO.AccountDAO;
import app.security.DAO.CartItemDAO;
import app.security.DAO.ProductDAO;
import app.security.DAO.ShoppingSessionDAO;
import app.security.DTO.CartDto;
import app.security.Entity.Account;
import app.security.Entity.Product;
import app.security.Entity.ShoppingSession;
import app.security.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemDAO cartItemDAO;

    private final ShoppingSessionDAO shoppingSessionDAO;

    private final AccountDAO accountDAO;

    private final ProductDAO productDAO;

    @Autowired
    public CartServiceImpl(final CartItemDAO cartItemDAO,
                           final ShoppingSessionDAO shoppingSessionDAO,
                           final AccountDAO accountDAO,
                           final ProductDAO productDAO) {
        this.cartItemDAO = cartItemDAO;
        this.shoppingSessionDAO = shoppingSessionDAO;
        this.accountDAO = accountDAO;
        this.productDAO = productDAO;
    }

    @Override
    public void setCart(final CartDto cartDto) {
        Account account = accountDAO.getUserByUsername(cartDto.getUsername());

        if (account != null) {
            ShoppingSession shoppingSession = shoppingSessionDAO.getShoppingSessionByAccount(account);
//            Product product = productDAO.
            if (shoppingSession != null) {

            }
        }
    }

    @Override
    public List<CartDto> findCartsByUsername(final String username) {
        return null;
    }

    @Override
    public void updateCart(final CartDto cartDto) {

    }

    @Override
    public void deleteCart() {

    }
}
