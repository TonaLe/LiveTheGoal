package app.security.Service;

import app.security.DTO.AccountDto;
import app.security.DTO.ProductDto;
import app.security.Entity.Product;

import java.util.List;

public interface ProductService {
    ProductDto loadProductByname(final String name);
    ProductDto loadProductById(final int id);
    ProductDto createOrUpdateProduct(final ProductDto product);

    List<ProductDto> getListProduct(final int limit, final int offset);

    ProductDto updateProductInfo(final int productId, final ProductDto productDto);

    void deleteProduct(final int id);
}
