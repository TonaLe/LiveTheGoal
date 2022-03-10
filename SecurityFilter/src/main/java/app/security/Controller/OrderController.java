package app.security.Controller;

import app.security.DTO.ErrorDto;
import app.security.DTO.OrderDto;
import app.security.DTO.ProductDto;
import app.security.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/Order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public Response addOrder(@RequestBody final OrderDto orderDto) {
        if (orderDto == null) return Response.status(Response.Status.BAD_REQUEST).build();

        List<ProductDto> outOfStockProduct
        if (orderService.validateQuantity(orderDto)) {
            return Response.status(new ErrorDto(10, "")).
        }
        orderService.setOrder(orderDto);
        return Response.status(Response.Status.OK).build();
    }
}
