package app.security.DAO.Impl;

import app.security.DAO.CategoryDAO;
import app.security.Entity.Category;
import app.security.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CategoryDaoImp implements CategoryDAO {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryDaoImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void addCategory(Category category) {
        categoryRepository.CreateCategory(category);
    }

    @Override
    public List<Category> getAllCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent();
    }
}
