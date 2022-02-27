package app.security.Service;

import app.security.DTO.CategoryDto;
import app.security.DTO.ProductDto;
import app.security.Entity.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> getListCategory(final int limit, final int offset);
    CategoryDto createCategory(final CategoryDto categoryDto);
    CategoryDto updateCategory(final String name, final CategoryDto categoryDto);

}
