package app.security.DAO.Impl;

import app.security.DAO.OrderDao;
import app.security.Entity.Order;
import app.security.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoImpl implements OrderDao {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDaoImpl(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void setOrder(final Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getOrderBySku(final String sku) {
        return null;
    }
}
