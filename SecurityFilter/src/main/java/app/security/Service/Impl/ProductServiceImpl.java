package app.security.Service.Impl;

import app.security.DAO.CategoryDAO;
import app.security.DAO.ProductDAO;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.DTO.ProductDto;
import app.security.Entity.Brand;
import app.security.Entity.Inventory;
import app.security.Entity.Product;
import app.security.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
    }



    @Override
    public ProductDto loadProductByName(String name) {
        var product = productDAO.loadProductByName(name);
        return convertEntityToDto(product);
    }

    @Override
    public ProductDto loadProductBySku(String sku) {
        var product = productDAO.loadProductBySku(sku);
        return convertEntityToDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product productDomain = modelMapper.map(productDto, Product.class);
        var brand = new Brand();
        var inventory = new Inventory();
        brand.setName(productDto.getName());
        inventory.setQuantity(productDto.getQuantity());
        productDomain.setBrand(brand);
        productDomain.setInventory(inventory);
        return convertEntityToDto(productDAO.setProduct(productDomain));
    }


    @Override
    public List<ProductDto> getListProduct(int limit, int offset) {
        Pageable pageable =  new OffsetBasedPageRequest(limit, offset, Sort.unsorted());
        List<Product> products = productDAO.findAllProduct(pageable);

        return products.stream()
                .filter(Objects::nonNull)
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProductInfo(String sku, ProductDto productDto) {
        LOG.info(String.format("Update information for product: %s", sku));

        Product product = productDAO.loadProductBySku(sku);
        Product productDomain = modelMapper.map(productDto, Product.class);
        var categoryInput = productDto.getCategory();
        var category = this.categoryDAO.findCategoryByName(categoryInput);
        if (product != null){
            product.setName(productDomain.getName());
            product.setDescription(productDomain.getDescription());
            product.setSku(productDomain.getSku());
            product.setPrice(productDomain.getPrice());
            product.setCategory(category);
        }
        productDAO.setProduct(product);
        return productDto;
    }


    @Override
    public void deleteProduct(String sku) {
        var product = modelMapper.map(loadProductBySku(sku), Product.class);
        product.setIsavailable(false);
        productDAO.setProduct(product);
    }

    private ProductDto convertEntityToDto(final Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setCategory(product.getCategory().getName());
        return productDto;
    }

}
