package app.security.DAO;

import app.security.Entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


/**
 * The interface Account dao.
 */
public interface ProductDAO {
    Product loadProductById(final int productId);
    Product createOrUpdateProduct(final Product product);
    Product loadProductByName(final String name);
    /**
     * Find all account account.
     *
     * @param pageable the pageable
     * @return the account
     */
    List<Product> findAllProduct(final Pageable pageable);
}
