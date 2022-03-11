package app.security.DAO.Impl;

import app.security.DAO.ProductDAO;
import app.security.Entity.Category;
import app.security.Entity.Product;
import app.security.Repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDAO {

    private final ProductRepository productRepository;

    public ProductDaoImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product setProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    public Product loadProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public Product loadProductBySku(String sku) {
        return productRepository.findProductBySku(sku);
    }

    @Override
    public List<Product> findAllProduct(Pageable pageable) {
        return productRepository.findAllByIsAvailable(true, pageable).getContent();
    }

    @Override
    public List<Product> findAllProductAdmin(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public int getTotalPage(Pageable pageable) {
        return productRepository.findAllByIsAvailable(true, pageable).getTotalPages();
    }

    @Override
    public int getTotalPageAdmin(Pageable pageable) {
        return productRepository.findAll(pageable).getTotalPages();
    }

    @Override
    public List<Product> findAllProductByCategory(Category category, Pageable pageable) {
        return productRepository.findAllByCategory(category,pageable).getContent();
    }

    @Override
    public int getTotalPageByCategory(final Category category, final Pageable pageable) {
        return productRepository.findAllByCategory(category, pageable).getTotalPages();
    }
}
