package app.security.DAO;

import app.security.Entity.Category;
import app.security.Entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


/**
 * The interface Account dao.
 */
public interface ProductDAO {
    Product setProduct(final Product product);
    Product loadProductByName(final String name);
    Product loadProductBySku(final String sku);
    List<Product> findAllProduct(final Pageable pageable);
    int getTotalPage(final Pageable pageable);
    List<Product> findAllProductByCategory(final Category category, Pageable pageable);
    int getTotalPageByCategory(final Category category, final Pageable pageable);
    List<Product> findAllProductAdmin(Pageable pageable);
    int getTotalPageAdmin(Pageable pageable);
}
