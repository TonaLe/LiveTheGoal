package app.security.Service.Impl;

import app.security.DAO.AccountDAO;
import app.security.DAO.CartItemDAO;
import app.security.DAO.ProductDAO;
import app.security.DAO.ShoppingSessionDAO;
import app.security.DTO.CartDto;
import app.security.Entity.Account;
import app.security.Entity.CartItem;
import app.security.Entity.Product;
import app.security.Entity.ShoppingSession;
import app.security.Service.CartService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.util.DateUtil.now;

/**
 * The type Cart service.
 */
@Service
public class CartServiceImpl implements CartService {

    /**
     * The Log.
     */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * The Cart item dao.
     */
    private final CartItemDAO cartItemDAO;

    /**
     * The Model mapper.
     */
    private final ModelMapper modelMapper;

    /**
     * The Shopping session dao.
     */
    private final ShoppingSessionDAO shoppingSessionDAO;

    /**
     * The Account dao.
     */
    private final AccountDAO accountDAO;

    /**
     * The Product dao.
     */
    private final ProductDAO productDAO;

    /**
     * Instantiates a new Cart service.
     *
     * @param cartItemDAO        the cart item dao
     * @param shoppingSessionDAO the shopping session dao
     * @param accountDAO         the account dao
     * @param productDAO         the product dao
     */
    @Autowired
    public CartServiceImpl(final CartItemDAO cartItemDAO,
                           final ShoppingSessionDAO shoppingSessionDAO,
                           final AccountDAO accountDAO,
                           final ProductDAO productDAO) {
        this.cartItemDAO = cartItemDAO;
        this.shoppingSessionDAO = shoppingSessionDAO;
        this.accountDAO = accountDAO;
        this.productDAO = productDAO;
        this.modelMapper =  new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public Integer setCart(final CartDto cartDto) {
        LOG.info(String.format("Setting Cart for account: %s", cartDto.getUsername()));

        Account account = accountDAO.getUserByUsername(cartDto.getUsername());

        if (account != null) {
            ShoppingSession shoppingSession = initShoppingCart(account, cartDto.getTotal());
            Product product = productDAO.loadProductBySku(cartDto.getSku());

            if (validateQuantity(product.getInventory().getQuantity(), cartDto.getQuantity())) {
                LOG.warn(String.format("Product %s is running out", cartDto.getSku()));
                return null;
            }

            if (shoppingSession == null) {
                LOG.warn("Can not find any shoppingSession or product");
                return null;
            }
            CartItem cartItem = modelMapper.map(cartDto, CartItem.class);

            if (cartItem != null) {
                initItemsToCart(cartItem, shoppingSession, product);
                cartItemDAO.setCartItem(cartItem);
                return cartItem.getId();
            }
        }
        return null;
    }

    @Override
    public List<CartDto> findCartsByUsername(final String username) {
        LOG.info(String.format("Find shopping cart for user %s", username));

        Account account = accountDAO.getUserByUsername(username);

        if (account == null) {
            return Collections.emptyList();
        }
        List<ShoppingSession> shoppingSession = account.getShoppingSessions();

        return shoppingSession.stream()
                .filter(Objects::nonNull)
                .map(shopping -> convertEntityToDto(shopping, shopping.getCartItems()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCart(final CartDto cartDto,
                           final Integer id) {
        LOG.info(String.format("Updating Cart for account: %s", cartDto.getUsername()));
        Product product = productDAO.loadProductBySku(cartDto.getSku());
        if (product == null || validateQuantity(product.getInventory().getQuantity(), cartDto.getQuantity())) {
            LOG.warn(String.format("Product %s is running out", cartDto.getSku()));
            return;
        }
        CartItem cartItem = cartItemDAO.findCartById(id);
        cartItem.setQuantity(cartDto.getQuantity());
        cartItemDAO.setCartItem(cartItem);
    }

    @Override
    public void deleteCart() {

    }

    /**
     * Init items to cart.
     *
     * @param cartItem        the cart item
     * @param shoppingSession the shopping session
     * @param product         the product
     */
    private void initItemsToCart(final CartItem cartItem,
                                 final ShoppingSession shoppingSession,
                                 final Product product) {
        cartItem.setShoppingSession(shoppingSession);
        cartItem.setProduct(product);
        cartItem.setCreatedAt(now());
        cartItem.setModifiedAt(now());
    }

    /**
     * Init shopping cart shopping session.
     *
     * @param account the account
     * @param total   the total
     * @return the shopping session
     */
    private ShoppingSession initShoppingCart(final Account account,
                                  final long total) {
        return ShoppingSession.builder()
                .account(account)
                .total(total)
                .createdAt(now())
                .modifiedAt(now())
                .build();
    }

    /**
     * Convert entity to dto list.
     *
     * @param shoppingSession the shopping session
     * @param cartItems       the cart items
     * @return the list
     */
    private List<CartDto> convertEntityToDto(final ShoppingSession shoppingSession,
                                       final List<CartItem> cartItems) {
        return cartItems.stream()
                .filter(Objects::nonNull)
                .map(item -> initCartDto(item, shoppingSession))
                .collect(Collectors.toList());
    }

    /**
     * Init cart dto cart dto.
     *
     * @param cartItem        the cart item
     * @param shoppingSession the shopping session
     * @return the cart dto
     */
    private CartDto initCartDto(final CartItem cartItem,
                             final ShoppingSession shoppingSession) {
        CartDto cartDto = modelMapper.map(cartItem, CartDto.class);

        cartDto.setTotal(shoppingSession.getTotal());
        cartDto.setUsername(shoppingSession.getAccount().getUsername());
        cartDto.setSku(cartItem.getProduct().getSku());
        return  cartDto;
    }

    private Boolean validateQuantity(final Integer inventoryQuantity,
                                     final Integer requestQuantity) {
        return inventoryQuantity < requestQuantity;
    }
}
