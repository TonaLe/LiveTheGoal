package app.security.Controller;

import app.security.DTO.CategoryDto;
import app.security.Entity.Category;
import app.security.Service.CategoryService;
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
@RequestMapping("/Category")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;
    private final ErrorService errorService;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CategoryController(final CategoryService categoryService,
                             final ErrorService errorService) {
        this.categoryService = categoryService;
        this.errorService = errorService;
    }

    @SneakyThrows
    @PostMapping
    public Response addCategory(@Valid @RequestBody CategoryDto category) {
        if (category == null) return Response.status(Response.Status.BAD_REQUEST).build();
        var data= categoryService.createCategory(category);
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @GetMapping("/")
    public Response getAllCategory(@RequestParam("limit") int limit,
                                            @RequestParam("offset") int offset) {
        var listCategory = categoryService.getListCategory(limit,offset);
        return Response.status(Response.Status.OK).entity(listCategory).build() ;
    }

    @GetMapping("/{name}")
    public Response getCategoryByName(@PathVariable String name) {
        var category = categoryService.loadCategoryByName(name);
        if (category == null){
            Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(category).build();
    }
}
