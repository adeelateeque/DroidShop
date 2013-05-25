package com.droidshop.api.controller;

import com.droidshop.api.manager.PurchaseRequestManager;
import com.droidshop.api.model.error.WebServiceError;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.purchase.PurchaseRequest;
import com.droidshop.api.model.purchase.PurchaseSearchCriteria;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/purchase")
public class PurchaseRequestController {
    @Autowired
	PurchaseRequestManager purchaseRequestManager;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public PurchaseRequest add(@RequestBody PurchaseRequest purchaseRequest) throws WebServiceException {
        System.out.println("AuthorizationRequestController.add [" + purchaseRequest.toString() + "]");
        PurchaseRequest response = purchaseRequestManager.add(purchaseRequest);
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public PurchaseRequest update(
            @PathVariable String id,
            @RequestBody PurchaseRequest purchaseRequest) throws WebServiceException
    {
        System.out.println("AuthorizationRequestController.update [" + purchaseRequest.toString() + "]");
        PurchaseRequest response = purchaseRequestManager.update(id, purchaseRequest);
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public PurchaseRequest get(@PathVariable String id) throws WebServiceException {
        System.out.println("AuthorizationRequestController.get");
        PurchaseRequest response = purchaseRequestManager.get(id);
        return response;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<PurchaseRequest> getAll() throws WebServiceException {
        System.out.println("AuthorizationRequestController.getAll");
        List<PurchaseRequest> response = purchaseRequestManager.getAll();
        return response;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<PurchaseRequest> search(
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

        List<PurchaseRequest> response = purchaseRequestManager.search(purchaseSearchCriteria);
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
