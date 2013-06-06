package com.droidshop.api.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.droidshop.api.dao.CategoryDAO;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.product.Category;

@Component
public class CategoryManager {
    @Autowired
    CategoryDAO categoryDAO;

    public Category add(Category category) throws Exception {
        System.out.println("CategoryManager: add");
        validate(category, POST.class);
        Category newCategory = categoryDAO.add(category);
        return newCategory;
    }

    public Category update(Long categoryID, Category category) throws Exception {
        System.out.println("CategoryManager: update");
        validate(category, PUT.class);

        if(Long.valueOf(categoryID) != Long.valueOf(category.getId())) {
            System.out.println("Category ID from URI [" + categoryID + "], Category ID from JSON [" +  category.getId() + "]");
            throw new Exception("Category ID Mismatch In Update Operation");
        }

        Category updatedCategory = categoryDAO.update(categoryID, category);
        return updatedCategory;
    }

    public Category fetch(Long categoryID) {
        System.out.println("CategoryManager: fetch");
        Category fetchedCategory = categoryDAO.fetch(categoryID);
        return fetchedCategory;
    }

    public Category fetchByCategoryName(String categoryName) {
        System.out.println("CategoryManager: fetchByCategoryName");
        Category fetchedCategory = categoryDAO.fetchByCategoryName(categoryName);
        return fetchedCategory;
    }

    public List<Category> fetchAll(boolean includeAll) {
        System.out.println("CategoryManager: fetchAll");
        List<Category> fetchedCategorys = categoryDAO.fetchAll(includeAll);
        return fetchedCategorys;
    }

    public Category delete(Long categoryID, Category category) throws Exception {
        System.out.println("CategoryManager: delete");
        validate(category, DELETE.class);

        if(Long.valueOf(categoryID) != Long.valueOf(category.getId())) {
            System.out.println("Category ID from URI [" + categoryID + "], Category ID from JSON [" +  category.getId() + "]");
            throw new Exception("Category ID Mismatch In Delete Operation");
        }

        Category deletedCategory = categoryDAO.delete(categoryID);
        return deletedCategory;
    }

    private <T> void validate(Category request, Class<T> T) throws WebServiceException {
        System.out.println("CategoryManager.validate");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Category>> violationSet = validator.validate(request, T);
        if(violationSet.size() > 0) {
            List<String> violationMessageList = new ArrayList<String>();
            for(ConstraintViolation<Category> violation : violationSet) {
                System.out.println("Violation [" + violation.getMessage() + "]");
                violationMessageList.add(violation.getMessage());
            }
            throw new WebServiceException(400, "Bad Input Request", violationMessageList);
        }
    }
}
