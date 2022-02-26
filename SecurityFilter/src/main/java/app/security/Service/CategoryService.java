package app.security.Service;

import app.security.DTO.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getListCategory(final int limit, final int offset);
    void createCategory(final CategoryDto categoryDto);
}
