package app.security.Service.Impl;

import app.security.DAO.AccountDAO;
import app.security.DAO.OrderDao;
import app.security.DAO.ProductDAO;
import app.security.DTO.ItemDto;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.DTO.OrderDto;
import app.security.DTO.ProductDto;
import app.security.Entity.Account;
import app.security.Entity.Order;
import app.security.Entity.OrderItems;
import app.security.Entity.Product;
import app.security.Service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.util.DateUtil.now;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    private final ProductDAO productDao;

    private final AccountDAO accountDao;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderServiceImpl(final OrderDao orderDao,
                            final ProductDAO productDao, final AccountDAO accountDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.accountDao = accountDao;
    }

    @Override
    public void setOrder(final OrderDto order) {
        LOG.info(StringUtils.join(StringUtils.SPACE,
                String.format("Insert order for user %s include items: *s", order.getUsername(), order.getItems().toString())));
        Account account = accountDao.getUserByUsername(order.getUsername());

        if (account == null) {
            LOG.error(String.format("account %s is not found", order.getUsername()));
            return;
        }

        Order orderDomain = Order.builder()
                .account(account)
                .items(getListOrderItems(order.getItems()))
                .total(order.getTotal())
                .createdAt(now())
                .modifiedAt(now())
                .build();

        orderDao.setOrder(orderDomain);
    }

    @Override
    public List<ProductDto> getOutOfStockProduct(final OrderDto orderDto) {
        if (orderDto.getItems().isEmpty()) {
            return Collections.EMPTY_LIST;
        }


        List<ItemDto> listItem = orderDto.getItems();
        ModelMapper modelMapper = new ModelMapper();
        List<ProductDto> outOfStockProducts = new ArrayList<>();
        for (ItemDto itemDto : listItem) {
            Product product = productDao.loadProductBySku(itemDto.getSku());
            if (product.getQuantity() < itemDto.getQuantity()) {
                outOfStockProducts.add(modelMapper.map(product, ProductDto.class));
            };
        }

        return outOfStockProducts;
    }

    private List<OrderItems> getListOrderItems(List<ItemDto> items) {
        if (items.isEmpty()) {
            return Collections.emptyList();
        }

        List<OrderItems> result = new ArrayList<>();
        for (ItemDto item : items) {
            Product product = productDao.loadProductBySku(item.getSku());
            OrderItems orderItem = OrderItems.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .createdAt(now())
                    .modifiedAt(now())
                    .build();
            result.add(orderItem);

            deductProductAmount(product, item.getQuantity());
        }

        return result;
    }

    private void deductProductAmount(Product product, int quantity) {
        int remain = product.getQuantity() - quantity;
        if (remain == 0) {
            product.setAvailable(false);
        }
        product.setQuantity(remain);
        productDao.setProduct(product);
    }

    @Override
    public List<OrderDto> findAll(final int limit, final int offset, final String sort, final String type) {
        Pageable pageable = getPageable(limit, offset, sort, type);
        List<Order> orders = orderDao.findAll(pageable);
        ModelMapper modelMapper = new ModelMapper();
        List<OrderDto> list = orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return list;
    }

    private Pageable getPageable(final int limit,
                                 final int offset,
                                 final String sort,
                                 final String sortType) {
        if (StringUtils.isBlank(sort)) {
            return new OffsetBasedPageRequest(limit, offset, Sort.unsorted());
        }

        if (sortType.equals("DESC")) {
            return new OffsetBasedPageRequest(limit, offset, Sort.by(sort).descending());
        }
        return new OffsetBasedPageRequest(limit, offset, Sort.by(sort).ascending());
    }

    private OrderDto convertToDto(Order order) {
        ModelMapper modelMapper = new ModelMapper();
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setUsername(order.getAccount().getUsername());
        orderDto.setItems(convertToListItemsDto(order.getItems()));
        return  orderDto;
    }

    private List<ItemDto> convertToListItemsDto(List<OrderItems> list) {
        List<ItemDto> result = new ArrayList<>();

        for (OrderItems item : list) {
            ItemDto itemDto = ItemDto.builder()
                    .sku(item.getProduct().getSku())
                    .quantity(item.getQuantity())
                    .build();

            result.add(itemDto);
        }

        return result;
    }
}
