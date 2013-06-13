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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.droidshop.api.dao.ProductDAO;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.product.Product;

@Service
public class ProductManager {
    @Autowired
    ProductDAO productDAO;

    @Transactional
    public Product add(Product product) throws Exception {
        System.out.println("ProductManager: add");
        validate(product, POST.class);
        Product newProduct = productDAO.add(product);
        return newProduct;
    }

    @Transactional
    public Product update(Long productID, Product product) throws Exception {
        System.out.println("ProductManager: update");
        validate(product, PUT.class);

        if(Long.valueOf(productID) != Long.valueOf(product.getId())) {
            System.out.println("Product ID from URI [" + productID + "], Product ID from JSON [" +  product.getId() + "]");
            throw new Exception("Product ID Mismatch In Update Operation");
        }

        Product updatedProduct = productDAO.update(productID, product);
        return updatedProduct;
    }

    @Transactional
    public Product fetch(Long productID) {
        System.out.println("ProductManager: fetch");
        Product fetchedProduct = productDAO.fetch(productID);
        return fetchedProduct;
    }

    @Transactional
    public Product fetchByProductName(String productName) {
        System.out.println("ProductManager: fetchByProductName");
        Product fetchedProduct = productDAO.fetchByProductName(productName);
        return fetchedProduct;
    }

    @Transactional
    public List<Product> fetchAll(boolean includeAll) {
        System.out.println("ProductManager: fetchAll");
        List<Product> fetchedProducts = productDAO.fetchAll(includeAll);
        return fetchedProducts;
    }

    @Transactional
    public Product delete(Long productID, Product product) throws Exception {
        System.out.println("ProductManager: delete");
        validate(product, DELETE.class);

        if(Long.valueOf(productID) != Long.valueOf(product.getId())) {
            System.out.println("Product ID from URI [" + productID + "], Product ID from JSON [" +  product.getId() + "]");
            throw new Exception("Product ID Mismatch In Delete Operation");
        }

        Product deletedProduct = productDAO.delete(productID);
        return deletedProduct;
    }

    private <T> void validate(Product request, Class<T> T) throws WebServiceException {
        System.out.println("ProductManager.validate");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Product>> violationSet = validator.validate(request, T);
        if(violationSet.size() > 0) {
            List<String> violationMessageList = new ArrayList<String>();
            for(ConstraintViolation<Product> violation : violationSet) {
                System.out.println("Violation [" + violation.getMessage() + "]");
                violationMessageList.add(violation.getMessage());
            }
            throw new WebServiceException(400, "Bad Input Request", violationMessageList);
        }
    }
}
