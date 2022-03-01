package app.security.Service.Impl;

import app.security.DAO.CategoryDAO;
import app.security.DAO.ProductDAO;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.DTO.ProductDto;
import app.security.Entity.Product;
import app.security.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
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
        return convertEntityToDto(productDAO.createOrUpdateProduct(productDomain));
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
    public ProductDto updateProductInfo(String name, ProductDto productDto) {
        LOG.info(String.format("Update information for product: %s", name));

        Product product = productDAO.loadProductByName(name);
        Product productDomain = modelMapper.map(productDto, Product.class);
        if (product != null){
            product.setName(productDomain.getName());
            product.setDescribe(productDomain.getDescribe());
            product.setSku(productDomain.getSku());
//            product.setBrandid(productDomain.getBrandid());
//            product.setInventoryid(productDomain.getInventoryid());
//            product.setCategoryid(productDomain.getCategoryid());
            product.setPrice(productDomain.getPrice());
        }
        return convertEntityToDto(productDAO.createOrUpdateProduct(product));
    }


    @Override
    public void deleteProduct(final String sku) {
        Product product = modelMapper.map(productDAO.loadProductBySku(sku), Product.class);
        product.setAvailable(false);
        productDAO.createOrUpdateProduct(product);
    }

    private ProductDto convertEntityToDto(final Product product) {
        ProductDto productDto =  modelMapper.map(product, ProductDto.class);
        productDto.setCategory(product.getCategory().getName());
        return productDto;
    }

}
