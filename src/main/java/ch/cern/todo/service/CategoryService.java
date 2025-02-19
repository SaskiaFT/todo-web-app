package ch.cern.todo.service;

import ch.cern.todo.DTO.CategoryDTO;
import ch.cern.todo.entity.Category;
import ch.cern.todo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {

        categoryRepository.deleteById(categoryId);
    }

    public Category updateCategory(Long categoryId, CategoryDTO categoryDetailsDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();

            existingCategory.setCategoryName(categoryDetailsDTO.getCategoryName());
            existingCategory.setCategoryDescription(categoryDetailsDTO.getCategoryDescription());

            return categoryRepository.save(existingCategory);
        } else {
            throw new RuntimeException("Category with ID " + categoryId + " not found.");
        }
    }
}
