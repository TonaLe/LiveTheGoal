package app.security.Controller;

import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.DTO.ProductDto;
import app.security.DTO.ProductResponse;
import app.security.Service.AccountService;
import app.security.Service.Impl.ErrorService;
import app.security.Service.ProductService;
import app.security.Utils.FileUploadUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
        final ProductResponse response = productService.getListProduct(limit, offset, sort, typeSort);
        if (response.getListProduct().isEmpty()) {
            Response.status(Response.Status.BAD_REQUEST).entity("No Product to be collected").build();
        }
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @SneakyThrows
    @PostMapping
    public Response createProduct(@Valid @RequestBody ProductDto productDto) {
        if (productDto == null) return Response.status(Response.Status.BAD_REQUEST).build();
        final ErrorDto errorDto = errorService.getSkuError(productDto);

        //image
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        productDto.setPic(fileName);

        if (errorDto != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorDto).build();
        }
        var newProduct = productService.createProduct(productDto);
        if (newProduct != null) {
//            String uploadDir = "product-photos/" + newProduct.getSku();
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            //end image
            return Response.status(Response.Status.OK).entity(newProduct).build();
        }
//        return Response.status(Response.Status.OK).entity(productDto).build();
         return Response.status(Response.Status.BAD_REQUEST).entity(null).build();

    }

    @PutMapping("/{sku}")
    public Response updateProduct(@PathVariable final String sku, @RequestBody final ProductDto productDto) {
        if (productDto == null || sku.equals("")) return Response.status(Response.Status.BAD_REQUEST).build();
        var updatedProduct = productService.updateProductInfo(sku, productDto);

        if (updatedProduct != null) {
            return Response.status(Response.Status.OK).entity(updatedProduct).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PostMapping("/AddImage/{sku}")
    public Response AddProductImage(@RequestParam("file") MultipartFile file, @PathVariable final String sku) throws IOException {
        if (file.equals("") || sku.equals("")) return Response.status(Response.Status.BAD_REQUEST).type("File hoặc sku rỗng.").build();

        var productToUpdate = productService.loadProductBySku(sku);

        //image
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        productToUpdate.setPic(fileName);

        var updatedProduct = productService.updateProductInfo(sku, productToUpdate);

        if (updatedProduct != null) {
            String uploadDir = "./product-photos/" + updatedProduct.getSku();
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            return Response.status(Response.Status.OK).entity(updatedProduct).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GetMapping("/byCategory/{categoryName}")
    public Response getProductsByCategoryId(@PathVariable final String categoryName,
                                            @RequestParam("limit") int limit,
                                            @RequestParam("offset") int offset,
                                            @RequestParam("sort") String sort,
                                            @RequestParam("type") String typeSort) {
        if (categoryName.equals("")) return Response.status(Response.Status.BAD_REQUEST).build();
        final ProductResponse productResponse = productService.getListProductByCategoryName(categoryName,
                limit, offset, sort, typeSort);
        return Response.status(Response.Status.OK).entity(productResponse).build();
    }


    @DeleteMapping ("/{sku}")
    public Response deleteProduct(@PathVariable final String sku) {
        if (sku.equals("")) return Response.status(Response.Status.BAD_REQUEST).build();
        productService.deleteProduct(sku);
        return Response.status(Response.Status.OK).build();
    }
}
