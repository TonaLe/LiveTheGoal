package app.security.Controller;

import app.security.DTO.CartDto;
import app.security.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/shop")
@CrossOrigin(origins = "http://localhost:3000")
public class ShoppingController {

    private final CartService cartService;

    @Autowired
    public ShoppingController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addToCart")
    public Response addToCart(@RequestBody final CartDto cartDto) {
        if (cartDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        cartService.setCart(cartDto);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
