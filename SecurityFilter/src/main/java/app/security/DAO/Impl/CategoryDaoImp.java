package app.security.DAO.Impl;

import app.security.DAO.CategoryDAO;
import app.security.Entity.Category;
import app.security.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CategoryDaoImp implements CategoryDAO {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryDaoImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category setCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public List<Category> getAllCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent();
    }
}
