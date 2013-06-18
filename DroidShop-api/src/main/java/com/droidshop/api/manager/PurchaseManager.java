package com.droidshop.api.manager;

import com.droidshop.api.dao.PurchaseDAO;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.purchase.Purchase;
import com.droidshop.api.model.purchase.PurchaseSearchCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PurchaseManager {
    @Autowired
	PurchaseDAO purchaseDAO;

    public Purchase add(Purchase purchase) throws WebServiceException {
        System.out.println("AuthorizationRequestManager.add");
        validate(purchase, POST.class);

        Purchase response = purchaseDAO.add(purchase);
        return response;
    }

    public Purchase update(String id, Purchase purchase) throws WebServiceException {
        System.out.println("AuthorizationRequestManager.update");
        validate(purchase, PUT.class);

        if(!purchase.getId().equals(id)) {
            throw new WebServiceException(400, "Purchase ID Mismatch");
        }
        Purchase response = purchaseDAO.update(id, purchase);
        return response;
    }
    
    public Purchase delete(String id, Purchase purchase) throws WebServiceException 
    {
        if(!purchase.getId().equals(id)) {
            throw new WebServiceException(400, "Purchase ID Mismatch");
        }
        Purchase response = purchaseDAO.delete(id, purchase);
        return response;
    }

    public Purchase get(String id) throws WebServiceException {
        System.out.println("AuthorizationRequestManager.get");
        Purchase response = purchaseDAO.get(id);
        return response;
    }

    public List<Purchase> getAll() throws WebServiceException {
        System.out.println("AuthorizationRequestManager.getAll");
        List<Purchase> response = purchaseDAO.getAll();
        return response;
    }

    public List<Purchase> search(PurchaseSearchCriteria purchaseSearchCriteria) throws WebServiceException {
        System.out.println("AuthorizationRequestManager.search");
        List<Purchase> response = purchaseDAO.search(purchaseSearchCriteria);
        return response;
    }

    private <T> void validate(Purchase request, Class<T> T) throws WebServiceException {
        System.out.println("PurchaseManager.validate");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Purchase>> violationSet = validator.validate(request, T);
        if(violationSet.size() > 0) {
            List<String> violationMessageList = new ArrayList<String>();
            for(ConstraintViolation<Purchase> violation : violationSet) {
                System.out.println("Violation [" + violation.getMessage() + "]");
                violationMessageList.add(violation.getMessage());
            }
            throw new WebServiceException(400, "Bad Input Request", violationMessageList);
        }
    }
}
