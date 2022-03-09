package app.security.Service;

import app.security.DTO.AccountDto;
import app.security.DTO.ProductDto;
import app.security.DTO.ProductResponse;
import app.security.Entity.Product;

import java.util.List;

public interface ProductService {
    ProductDto loadProductByName(final String name);

    ProductDto loadProductBySku(final String sku);

    ProductDto createProduct(final ProductDto product);

    ProductResponse getListProduct(final int limit, final int offset, final String sort, final String sortType);

    ProductDto updateProductInfo(final String sku, final ProductDto productDto);

    void deleteProduct(final String sku);
}
