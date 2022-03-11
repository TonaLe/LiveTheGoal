package app.security.Controller;

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

        List<ProductDto> outOfStockProduct = orderService.getOutOfStockProduct(orderDto);
        if (!outOfStockProduct.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(outOfStockProduct).build();
        }
        orderService.setOrder(orderDto);
        return Response.status(Response.Status.OK).build();
    }

    @GetMapping("/Admin/Info")
    public Response getOrder(@RequestParam("limit") int limit,
                             @RequestParam("offset") int offset,
                             @RequestParam("sort") String sort,
                             @RequestParam("type") String typeSort) {
        List<OrderDto> orderDtos = orderService.findAll(limit, offset, sort, typeSort);

        if (orderDtos.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Could not find any orders").build();
        }

        return Response.status(Response.Status.OK).entity(orderDtos).build();
    }
    
}
