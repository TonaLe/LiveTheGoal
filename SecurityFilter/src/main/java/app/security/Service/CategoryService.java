package app.security.Service;

import app.security.DTO.AccountDto;
import app.security.DTO.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<AccountDto> getListCategory(final int limit, final int offset);
    void createCategory(final CategoryDto categoryDto);
}
