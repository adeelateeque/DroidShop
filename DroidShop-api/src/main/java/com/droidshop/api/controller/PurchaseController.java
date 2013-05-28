package com.droidshop.api.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import com.droidshop.api.manager.PurchaseRequestManager;
import com.droidshop.api.model.error.WebServiceError;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.purchase.Purchase;
import com.droidshop.api.model.purchase.PurchaseSearchCriteria;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
	PurchaseRequestManager purchaseRequestManager;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Purchase add(@RequestBody Purchase purchase) throws WebServiceException {
        System.out.println("AuthorizationRequestController.add [" + purchase.toString() + "]");
        Purchase response = purchaseRequestManager.add(purchase);
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Purchase update(
            @PathVariable String id,
            @RequestBody Purchase purchase) throws WebServiceException
    {
        System.out.println("AuthorizationRequestController.update [" + purchase.toString() + "]");
        Purchase response = purchaseRequestManager.update(id, purchase);
        return response;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Purchase delete(@PathVariable String id, @RequestBody Purchase purchase) throws Exception {
        return purchaseRequestManager.delete(id, purchase);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Purchase get(@PathVariable String id) throws WebServiceException {
        System.out.println("AuthorizationRequestController.get");
        Purchase response = purchaseRequestManager.get(id);
        return response;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Purchase> getAll() throws WebServiceException {
        System.out.println("AuthorizationRequestController.getAll");
        List<Purchase> response = purchaseRequestManager.getAll();
        return response;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Purchase> search(
            @RequestParam(value = "user_ip_address", required = false) String userIPAddress,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "order_number", required = false) String orderNumber) throws WebServiceException
    {
        System.out.println("AuthorizationRequestController.search");

        PurchaseSearchCriteria purchaseSearchCriteria = new PurchaseSearchCriteria();
        if(!StringUtils.isEmpty(userIPAddress)) {
            purchaseSearchCriteria.setUserIPAddress(userIPAddress);
        }
        if(!StringUtils.isEmpty(email)) {
            purchaseSearchCriteria.setEmail(email);
        }
        if(!StringUtils.isEmpty(clientId)) {
            purchaseSearchCriteria.setClientId(clientId);
        }
        if(!StringUtils.isEmpty(orderNumber)) {
            purchaseSearchCriteria.setOrderNumber(orderNumber);
        }

        List<Purchase> response = purchaseRequestManager.search(purchaseSearchCriteria);
        return response;
    }

    // Exception handler for WebServiceException cases
    @ExceptionHandler(WebServiceException.class)
    public ResponseEntity<WebServiceError> handleWebServiceException(WebServiceException webServiceException) {
        System.out.println("AuthorizationRequestController.handleWebServiceException");
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
        System.out.println("AuthorizationRequestController.handleException");
        WebServiceError webServiceError = new WebServiceError(400, exception.getMessage());
        return new ResponseEntity<WebServiceError>(webServiceError, HttpStatus.BAD_REQUEST);
    }
}
