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
@RequestMapping("/Product")
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/{name}")
    public Response getProductByName(@PathVariable String name) {
        var product = productService.loadProductByName(name);
        if (product == null){
            Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(product).build();
    }

    @GetMapping
    public Response getProductBySku(@RequestParam("sku") String sku) {
        var product = productService.loadProductBySku(sku);
        if (product == null){
            Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(product).build();
    }

    @GetMapping("/Info")
    public Response getProductList(@RequestParam("limit") int limit,
                                   @RequestParam("offset") int offset,
                                   @RequestParam("sort") String sort,
                                   @RequestParam("type") String typeSort) {
        final List<ProductDto> listProduct = productService.getListProduct(limit, offset, sort, typeSort);
        if (listProduct.isEmpty()) {
            Response.status(Response.Status.BAD_REQUEST).entity("No Product to be collected").build();
        }
        return Response.status(Response.Status.OK).entity(listProduct).build();
    }

    @SneakyThrows
    @PostMapping
    public Response createProduct(@Valid @RequestBody ProductDto productDto) {
        if (productDto == null) return Response.status(Response.Status.BAD_REQUEST).build();
        var newProduct = productService.createProduct(productDto);
        if (newProduct != null) {
            return Response.status(Response.Status.OK).entity(newProduct).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PutMapping("/{sku}")
    public Response updateProduct(@PathVariable final String sku, @RequestBody final ProductDto productDto) {
        if (productDto == null || sku == "") return Response.status(Response.Status.BAD_REQUEST).build();
        var updatedProduct = productService.updateProductInfo(sku, productDto);

        if (updatedProduct != null) {
            return Response.status(Response.Status.OK).entity(updatedProduct).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



    @DeleteMapping ("/{sku}")
    public Response deleteProduct(@PathVariable final String sku) {
        if (sku == "") return Response.status(Response.Status.BAD_REQUEST).build();
        productService.deleteProduct(sku);
        return Response.status(Response.Status.OK).build();
    }
}
