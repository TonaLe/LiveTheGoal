package app.security.Controller;

import app.security.DTO.BrandDto;
import app.security.DTO.ProductDto;
import app.security.Service.BrandService;
import app.security.Service.Impl.ErrorService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/Brand")
@CrossOrigin(origins = "http://localhost:3000")
public class BrandController {
    private final BrandService brandService;
    private final ErrorService errorService;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired

    public BrandController(BrandService brandService, final ErrorService errorService) {
        this.brandService = brandService;
        this.errorService = errorService;
    }

    @GetMapping("/Info")
    public Response getBrandList(@RequestParam("limit") int limit,
                                   @RequestParam("offset") int offset) {
        final List<BrandDto> brandDtoList = brandService.getListBrand(limit, offset);
        if (brandDtoList.isEmpty()) {
            Response.status(Response.Status.BAD_REQUEST).entity("No Brand to be collected").build();
        }
        return Response.status(Response.Status.OK).entity(brandDtoList).build();
    }

    @GetMapping
    public Response getBrandByName(@RequestParam("name") String name) {
        var brand = brandService.loadBrandByName(name);
        if (brand == null){
            Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(brand).build();
    }

    @SneakyThrows
    @PostMapping
    public Response createBrand(@Valid @RequestBody BrandDto brandDto) {
        if (brandDto == null) return Response.status(Response.Status.BAD_REQUEST).build();
        var newBrand = brandService.createBrand(brandDto);
        if (newBrand != null) {
            return Response.status(Response.Status.OK).entity(newBrand).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
