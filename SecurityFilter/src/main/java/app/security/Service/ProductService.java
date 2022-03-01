package app.security.Service;

import app.security.DTO.AccountDto;
import app.security.DTO.ProductDto;
import app.security.Entity.Product;

import java.util.List;

public interface ProductService {
    ProductDto loadProductByName(final String name);

    ProductDto loadProductBySku(final String sku);

    ProductDto createProduct(final ProductDto product);

    List<ProductDto> getListProduct(final int limit, final int offset);

    ProductDto updateProductInfo(final String name, final ProductDto productDto);

    void deleteProduct(final String sku);
}
