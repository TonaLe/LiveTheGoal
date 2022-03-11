package app.security.Service;

import app.security.DTO.OrderDto;
import app.security.DTO.ProductDto;

import java.util.List;

public interface OrderService {
    void setOrder(final OrderDto order);
    List<ProductDto> getOutOfStockProduct(final OrderDto orderDto);
    List<OrderDto> findAll(int limit, int offset, String sort, String type);
}
