package app.security.Controller;

import app.security.DTO.CategoryDto;
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
@RequestMapping("/category")
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
    @PostMapping("/add")
    public Response addCategory(@Valid @RequestBody CategoryDto category) {
        if (category == null) return Response.status(Response.Status.BAD_REQUEST).build();
        categoryService.createCategory(category);
        return Response.status(Response.Status.OK).build();
    }

    @GetMapping("/")
    public List<CategoryDto> getAllCategory(@RequestParam("limit") int limit,
                                            @RequestParam("offset") int offset) {
        var listCategory = categoryService.getListCategory(limit,offset);
        return listCategory;
    }
}
