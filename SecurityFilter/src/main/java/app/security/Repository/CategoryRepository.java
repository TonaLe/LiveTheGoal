package app.security.Repository;

import app.security.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    void CreateCategory(Category category);

    Page<Category> findAll(final Pageable pageable);

}
