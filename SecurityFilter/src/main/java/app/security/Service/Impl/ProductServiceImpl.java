package app.security.Service.Impl;

import app.security.DAO.AccountDAO;
import app.security.DAO.AccountDetailDAO;
import app.security.DAO.CategoryDAO;
import app.security.DAO.ProductDAO;
import app.security.DTO.AccountDto;
import app.security.DTO.CategoryDto;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.DTO.ProductDto;
import app.security.Entity.Account;
import app.security.Entity.AccountDetail;
import app.security.Entity.Category;
import app.security.Entity.Product;
import app.security.Enum.Role;
import app.security.Service.CategoryService;
import app.security.Service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    public ProductDto loadProductByname(String name) {
        var product = productDAO.loadProductByName(name);
        return convertEntityToDto(product);
    }

    @Override
    public ProductDto loadProductById(int id) {
        var product = productDAO.loadProductById(id);
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
    public ProductDto updateProductInfo(int productId, ProductDto productDto) {
        LOG.info(String.format("Update information for product: %s", productId));

        Product product = productDAO.loadProductById(productId);
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
    public void deleteProduct(int id) {
        var product = modelMapper.map(loadProductById(id), Product.class);
        product.setAvailable(false);
        productDAO.createOrUpdateProduct(product);
    }

    private ProductDto convertEntityToDto(final Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

}
