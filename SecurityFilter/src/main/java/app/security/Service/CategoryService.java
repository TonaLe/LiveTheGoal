package app.security.Service;

import app.security.DTO.CategoryDto;
import app.security.DTO.ProductDto;
import app.security.Entity.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> getListCategory(final int limit, final int offset);
    CategoryDto findCategoryById(final int id);
    CategoryDto createCategory(final CategoryDto categoryDto);
    CategoryDto updateCategoryInfo(final int id, final CategoryDto categoryDto);

}
