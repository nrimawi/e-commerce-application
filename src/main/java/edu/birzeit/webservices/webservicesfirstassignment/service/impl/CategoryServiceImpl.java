package edu.birzeit.webservices.webservicesfirstassignment.service.impl;


import edu.birzeit.webservices.webservicesfirstassignment.dto.CategoryDto;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Category;
import edu.birzeit.webservices.webservicesfirstassignment.exception.ResourceNotFoundException;
import edu.birzeit.webservices.webservicesfirstassignment.repository.CategoryRepository;
import edu.birzeit.webservices.webservicesfirstassignment.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //To enable this class for component scanning
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository CategoryRepository;

    public CategoryServiceImpl(edu.birzeit.webservices.webservicesfirstassignment.repository.CategoryRepository CategoryRepository) {
        this.CategoryRepository = CategoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto CategoryDto) {

        try {
            // convert DTO to entity
            Category Category = mapToEntity(CategoryDto);
            Category.setIsActive(true);
            Category newCategory = CategoryRepository.save(Category);

            // convert entity to DTO
            CategoryDto CategoryResponse = mapToDTO(newCategory);
            return CategoryResponse;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        try {

            List<Category> categories = CategoryRepository.findAll();
            ArrayList<CategoryDto> categoriesDto = new ArrayList<CategoryDto>();

            for (Category category : categories)
                if (category.getIsActive())
                    categoriesDto.add(mapToDTO(category));

            return categoriesDto;
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public CategoryDto getCategoryById(long id) {
        try {
            Category Category = CategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
            if (!Category.getIsActive())
                throw new ResourceNotFoundException("Category", "id", id);
            return mapToDTO(Category);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto CategoryDto, long id) {
        try {
            // get Category by id from the database
            Category Category = CategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
            if (Category.getIsActive()) {
                CategoryDto.setId(id);
                Category = mapToEntity(CategoryDto);
                Category.setIsActive(true);
            }
            Category updatedCategory = CategoryRepository.save(Category);

            return mapToDTO(updatedCategory);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteCategoryById(long id) {
        try {
            // get Category by id from the database
            Category Category = CategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
            if (!Category.getIsActive())
                throw new ResourceNotFoundException("Category", "id", id);
            Category.setIsActive(false);
            CategoryRepository.save(Category);
        } catch (Exception e) {
            throw e;
        }
    }

    private CategoryDto mapToDTO(Category Category) {
        try {
            CategoryDto CategoryDto = new CategoryDto();
            CategoryDto.setId(Category.getId());
            CategoryDto.setDescription(Category.getDescription());
            CategoryDto.setName(Category.getName());
            return CategoryDto;
        } catch (Exception e) {
            throw e;
        }
    }

    private Category mapToEntity(CategoryDto CategoryDto) {
        try {
            Category Category = new Category();
            Category.setId(CategoryDto.getId());
            Category.setDescription(CategoryDto.getDescription());
            Category.setName(CategoryDto.getName());
            return Category;
        } catch (Exception e) {
            throw e;
        }
    }
}