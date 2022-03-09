//package app.security.Service.Impl;
//
//import app.security.DAO.OrderDao;
//import app.security.DAO.ProductDAO;
//import app.security.DTO.ItemDto;
//import app.security.DTO.OrderDto;
//import app.security.Entity.Product;
//import app.security.Service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderServiceImpl implements OrderService {
//
//    private final OrderDao orderDao;
//
//    private final ProductDAO productDao;
//
//    @Autowired
//    public OrderServiceImpl(final OrderDao orderDao,
//                            final ProductDAO productDao) {
//        this.orderDao = orderDao;
//        this.productDao = productDao;
//    }
//
//    @Override
//    public void setOrder(final OrderDto order) {
//
//    }
//
//    @Override
//    public Boolean validateQuantity(final OrderDto orderDto) {
//        if (orderDto.getItems().isEmpty()) {
//            return false;
//        }
//
//        List<Product> productList = orderDto.getItems().stream()
//                .map(item -> productDao.loadProductBySku(item.getSku()))
//                .collect(Collectors.toList());
//
//        Boolean qualify;
//        final List<ItemDto> items = orderDto.getItems();
//        for(Product product : productList ) {
//            item.
//        }
//        return quantity <= product.getQuantity();
//    }
//}
