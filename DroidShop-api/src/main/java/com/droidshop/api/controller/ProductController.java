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

import com.droidshop.api.manager.ProductManager;
import com.droidshop.api.model.Product;
import com.droidshop.api.model.error.WebServiceError;
import com.droidshop.api.model.error.WebServiceException;


@Controller
@RequestMapping("/product")
public class ProductController
{
	    @Autowired
	    ProductManager productManager;

	    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	    @ResponseBody
	    public Product addProduct(@RequestBody Product product) throws Exception {
	        return productManager.add(product);
	    }

	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	    @ResponseBody
	    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) throws Exception {
	        return productManager.update(id, product);
	    }

	    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	    @ResponseBody
	    public Product fetchProduct(@PathVariable Long id) {
	        return productManager.fetch(id);
	    }

	    @RequestMapping(value = "/name/{productName}", method = RequestMethod.GET, produces = "application/json")
	    @ResponseBody
	    public Product fetchProductByname(@PathVariable String productName) {
	        return productManager.fetchByProductName(productName);
	    }

	    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	    @ResponseBody
	    public List<Product> fetchAllProducts(@RequestParam(value = "include_all", required = false) boolean includeAll) {
	        return productManager.fetchAll(includeAll);
	    }

	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	    @ResponseBody
	    public Product deleteProduct(@PathVariable Long id, @RequestBody Product product) throws Exception {
	        return productManager.delete(id, product);
	    }

	    // Exception handler for WebServiceException cases
	    @ExceptionHandler(WebServiceException.class)
	    public ResponseEntity<WebServiceError> handleWebServiceException(WebServiceException webServiceException) {
	        System.out.println("ProductController.handleWebServiceException");
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
	        System.out.println("ProductController.handleException");
	        WebServiceError webServiceError = new WebServiceError(400, exception.getMessage());
	        return new ResponseEntity<WebServiceError>(webServiceError, HttpStatus.BAD_REQUEST);
	    }
	}
