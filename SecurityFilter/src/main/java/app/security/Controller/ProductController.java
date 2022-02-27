package app.security.Controller;

import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.DTO.ProductDto;
import app.security.Service.AccountService;
import app.security.Service.Impl.ErrorService;
import app.security.Service.ProductService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ErrorService errorService;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductController(final ProductService productService,
                             final ErrorService errorService) {
        this.productService = productService;
        this.errorService = errorService;
    }

    @GetMapping("/Product/{id}")
    public Response getProductById(@PathVariable String id) {
        var product = productService.loadProductById(parseInt(id));
        return Response.status(Response.Status.OK).entity(product).build();
    }

    @GetMapping("/Products")
    public Response getProductList(@RequestParam("limit") int limit,
                                   @RequestParam("offset") int offset) {
        final List<ProductDto> listProduct = productService.getListProduct(limit, offset);
        if (listProduct.isEmpty()) {
            Response.status(Response.Status.BAD_REQUEST).entity("No Product to be collected").build();
        }
        return Response.status(Response.Status.OK).entity(listProduct).build();
    }

    @SneakyThrows
    @PostMapping("/AddProduct")
    public Response createProduct(@Valid @RequestBody ProductDto productDto) {
        if (productDto == null) return Response.status(Response.Status.BAD_REQUEST).build();
        var newProduct = productService.createProduct(productDto);

        if (newProduct != null) {
            return Response.status(Response.Status.OK).entity(newProduct).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PutMapping("/Product/{id}")
    public Response updateProduct(@PathVariable final String id, @RequestBody final ProductDto productDto) {
        if (productDto == null || id == "") return Response.status(Response.Status.BAD_REQUEST).build();
        var updatedProduct = productService.updateProductInfo(parseInt(id), productDto);

        if (updatedProduct != null) {
            return Response.status(Response.Status.OK).entity(updatedProduct).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DeleteMapping ("/Product/{id}")
    public Response deleteProduct(@PathVariable final String id) {
        if (id == "") return Response.status(Response.Status.BAD_REQUEST).build();
        productService.deleteProduct(parseInt(id));
        return Response.status(Response.Status.OK).build();
    }
}