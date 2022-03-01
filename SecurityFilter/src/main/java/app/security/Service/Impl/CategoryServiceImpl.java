package app.security.Service.Impl;

import app.security.DAO.CategoryDAO;
import app.security.DTO.CategoryDto;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.Entity.Category;
import app.security.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    private final CategoryDAO categoryDAO;
    @Autowired
    public CategoryServiceImpl(final CategoryDAO categoryDAO) {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<CategoryDto> getListCategory(int limit, int offset) {
        Pageable pageable =  new OffsetBasedPageRequest(limit, offset, Sort.unsorted());
        List<Category> categoryList = categoryDAO.getAllCategory(pageable);

        return categoryList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category categoryDomain = modelMapper.map(categoryDto, Category.class);
        return convertEntityToDto(categoryDAO.setCategory(categoryDomain));
    }

    @Override
    public CategoryDto loadCategoryByName(String name) {
        var category = categoryDAO.findCategoryByName(name);
        return convertEntityToDto(category);
    }

    private CategoryDto convertEntityToDto(final Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
