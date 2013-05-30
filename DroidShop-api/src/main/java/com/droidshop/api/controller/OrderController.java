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

import com.droidshop.api.manager.OrderManager;
import com.droidshop.api.model.error.WebServiceError;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.order.Order;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderManager orderManager;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Order addOrder(@RequestBody Order order) throws Exception {
        return orderManager.add(order);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) throws Exception {
        return orderManager.update(id, order);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Order fetchOrder(@PathVariable Long id) {
        return orderManager.fetch(id);
    }

    @RequestMapping(value = "/username/{userName}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Order fetchByUserName(@PathVariable String userName) {
        return orderManager.fetchByUserName(userName);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Order> fetchAllOrders(@RequestParam(value = "include_all", required = false) boolean includeAll) {
        return orderManager.fetchAll(includeAll);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Order deleteOrder(@PathVariable Long id, @RequestBody Order order) throws Exception {
        return orderManager.delete(id, order);
    }

    // Exception handler for WebServiceException cases
    @ExceptionHandler(WebServiceException.class)
    public ResponseEntity<WebServiceError> handleWebServiceException(WebServiceException webServiceException) {
        System.out.println("OrderController.handleWebServiceException");
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
        System.out.println("OrderController.handleException");
        WebServiceError webServiceError = new WebServiceError(400, exception.getMessage());
        return new ResponseEntity<WebServiceError>(webServiceError, HttpStatus.BAD_REQUEST);
    }
}
