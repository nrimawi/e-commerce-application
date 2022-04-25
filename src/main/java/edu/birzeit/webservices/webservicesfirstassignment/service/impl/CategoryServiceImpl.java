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

        // convert DTO to entity
        Category Category = mapToEntity(CategoryDto);
        Category newCategory = CategoryRepository.save(Category);

        // convert entity to DTO
        CategoryDto CategoryResponse = mapToDTO(newCategory);
        return CategoryResponse;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = CategoryRepository.findAll();
        ArrayList<CategoryDto> categoriesDto=new ArrayList<CategoryDto>();

        for (Category  category:categories)
            if(category.getIsActive())
                categoriesDto.add(mapToDTO(category));

        return  categoriesDto;
    }



    @Override
    public CategoryDto getCategoryById(long id) {
        Category Category = CategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        if (!Category.getIsActive())
            throw new ResourceNotFoundException("Category", "id", id);
        return mapToDTO(Category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto CategoryDto, long id) {
        // get Category by id from the database
        Category Category = CategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        if(Category.getIsActive()) {
            Category.setName(CategoryDto.getName());
            Category.setDescription(CategoryDto.getDescription());
            Category.setCreationDate(CategoryDto.getCreationDate());
        }
        Category updatedCategory = CategoryRepository.save(Category);

        return mapToDTO(updatedCategory);
    }

    @Override
    public void deleteCategoryById(long id) {
        // get Category by id from the database
        Category Category = CategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        Category.setIsActive(false);
        CategoryRepository.save(Category);
    }

    private CategoryDto mapToDTO(Category Category){
        CategoryDto CategoryDto = new CategoryDto();
        CategoryDto.setId(Category.getId());
        CategoryDto.setCreationDate(Category.getCreationDate());
        CategoryDto.setDescription(Category.getDescription());
        CategoryDto.setName(Category.getName());
        return CategoryDto;
    }

    private Category mapToEntity(CategoryDto CategoryDto){
        Category Category = new Category();
        Category.setId(CategoryDto.getId());
        Category.setCreationDate(CategoryDto.getCreationDate());
        Category.setDescription(CategoryDto.getDescription());
        Category.setName(CategoryDto.getName());
        return Category;
    }
}
