package app.security.DAO;
import app.security.Entity.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;
/**
 * The interface Category dao.
 */
public interface CategoryDAO {
    Category setCategory(final Category category);
    Category findCategoryByName(final String name);
    List<Category> getAllCategory(final Pageable pageable);
}
