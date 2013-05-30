package com.droidshop.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.droidshop.api.manager.CategoryManager;
import com.droidshop.api.model.error.WebServiceError;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.product.Category;


@Controller
@RequestMapping("/category")
public class CategoryController
{
	    @Autowired
	    CategoryManager categoryManager;

	    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	    @ResponseBody
	    public Category addCategory(@RequestBody Category category) throws Exception {
	        return categoryManager.add(category);
	    }

	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	    @ResponseBody
	    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) throws Exception {
	        return categoryManager.update(id, category);
	    }

	    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	    @ResponseBody
	    public Category fetchCategory(@PathVariable Long id) {
	        return categoryManager.fetch(id);
	    }

	    @RequestMapping(value = "/name/{categoryName}", method = RequestMethod.GET, produces = "application/json")
	    @ResponseBody
	    public Category fetchCategoryByname(@PathVariable String categoryName) {
	        return categoryManager.fetchByCategoryName(categoryName);
	    }

	    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	    @ResponseBody
	    public List<Category> fetchAllCategorys(@RequestParam(value = "include_all", required = false) boolean includeAll) {
	        return categoryManager.fetchAll(includeAll);
	    }

	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	    @ResponseBody
	    public Category deleteCategory(@PathVariable Long id, @RequestBody Category category) throws Exception {
	        return categoryManager.delete(id, category);
	    }

	    // Exception handler for WebServiceException cases
	    @ExceptionHandler(WebServiceException.class)
	    public ResponseEntity<WebServiceError> handleWebServiceException(WebServiceException webServiceException) {
	        System.out.println("CategoryController.handleWebServiceException");
	        WebServiceError webServiceError = new WebServiceError(webServiceException.getExceptionCode(), webServiceException.getExceptionMessage());

	        // This should happen in case of input constraint validations
	        if(webServiceException.getExceptionMessageList() != null) {
	            webServiceError.setErrorMessageList(webServiceException.getExceptionMessageList());
	        }
	        return new ResponseEntity<WebServiceError>(webServiceError, HttpStatus.BAD_REQUEST);
	    }

	    // Exception handler for generic Exception cases
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<WebServiceError> handleException(Exception exception) {
	        System.out.println("CategoryController.handleException");
	        WebServiceError webServiceError = new WebServiceError(400, exception.getMessage());
	        return new ResponseEntity<WebServiceError>(webServiceError, HttpStatus.BAD_REQUEST);
	    }
	}
