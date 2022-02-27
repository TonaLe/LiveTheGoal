package app.security.DAO;
import app.security.Entity.Category;
import app.security.Entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
/**
 * The interface Category dao.
 */
public interface CategoryDAO {
    Category findCategoryById(final int id);
    Category createOrUpdateCategory(final Category category);
    List<Category> getAllCategory(final Pageable pageable);
}
