package app.security.Repository;

import app.security.Entity.Account;
import app.security.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductByName(final String name);
    Product findProductBySku(final String sku);
    Page<Product> findAll(final Pageable pageable);
}
