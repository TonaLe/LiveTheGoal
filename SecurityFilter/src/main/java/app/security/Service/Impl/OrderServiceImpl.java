package app.security.Service.Impl;

import app.security.DAO.OrderDao;
import app.security.DTO.OrderDto;
import app.security.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(final OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void setOrder(final OrderDto order) {

    }
}
