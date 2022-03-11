package app.security.DAO;

import app.security.Entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Order dao.
 */
public interface OrderDao {
    /**
     * Sets order.
     *
     * @param order the order
     */
    void setOrder(final Order order);

    Order getOrderBySku(final String sku);

    List<Order> findAll(Pageable pageable);

    int getTotalPage(final Pageable pageable);
}
