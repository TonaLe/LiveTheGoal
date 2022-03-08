package app.security.Service.Impl;

import app.security.DAO.BrandDAO;
import app.security.DAO.CategoryDAO;
import app.security.DAO.ProductDAO;
import app.security.DTO.BrandDto;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.DTO.ProductDto;
import app.security.Entity.Product;
import app.security.Service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final BrandDAO brandDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO, CategoryDAO categoryDAO, BrandDAO brandDAO) {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
        this.brandDAO = brandDAO;
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
        //Category
        var categoryName = productDto.getCategory();
        var category = this.categoryDAO.findCategoryByName(categoryName);
        productDomain.setCategory(category);
        //Brand
        var brandName = productDto.getBrand();
        var brand = this.brandDAO.findBrandByName(brandName);
        productDomain.setBrand(brand);
        //IsActive
        productDomain.setAvailable(true);
        //Add
        var newProduct = productDAO.setProduct(productDomain);
        return convertEntityToDto(productDAO.setProduct(newProduct));
    }


    @Override
    public List<ProductDto> getListProduct(final int limit,
                                           final int offset,
                                           final String sort,
                                           final String sortType) {
        Pageable pageable = null;

        if (StringUtils.isBlank(sort)) {
            pageable =  new OffsetBasedPageRequest(limit, offset, Sort.unsorted());
        } else {
            if (sortType.equals("DESC")) {
                pageable =  new OffsetBasedPageRequest(limit, offset, Sort.by(sort).descending());
            } else {
                pageable =  new OffsetBasedPageRequest(limit, offset, Sort.by(sort).ascending());
            }
        }
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
        var brandInput = productDto.getBrand();
        var brand = this.brandDAO.findBrandByName(brandInput);
        if (product != null){
            product.setName(productDomain.getName());
            product.setDescription(productDomain.getDescription());
            product.setPrice(productDomain.getPrice());
            product.setCategory(category);
            product.setModifiedAt(new Date());
            product.setBrand(brand);
            product.setAvailable(true);
            product.setSku(productDomain.getSku());
            product.setQuantity(productDomain.getQuantity());
            productDAO.setProduct(product);
            return productDto;
        }
        return null;
    }


    @Override
    public void deleteProduct(String sku) {
        var product = modelMapper.map(loadProductBySku(sku), Product.class);
        product.setAvailable(false);
        productDAO.setProduct(product);
    }

    private ProductDto convertEntityToDto(final Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setCategory(product.getCategory().getName());
        productDto.setBrand(product.getBrand().getName());
        return productDto;
    }

}
