package com.droidshop.api.dao;

import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.purchase.Purchase;
import com.droidshop.api.model.purchase.PurchaseSearchCriteria;
import com.droidshop.api.util.mongodb.MongoBroker;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseDAO {
    @Autowired
    MongoBroker mongoBroker;

    public Purchase add(Purchase purchase) throws WebServiceException {
        System.out.println("AuthorizationRequestDAO.add");

        try {
            mongoBroker.getTemplate().save(purchase);
        } catch(Exception exception) {
            System.out.println("Exception [" + exception.getMessage() + "]");
            throw new WebServiceException(500, exception.getMessage());
        }
        return purchase;
    }

    public Purchase update(String id, Purchase purchase) throws WebServiceException {
        System.out.println("AuthorizationRequestDAO.update");

        Purchase updatedRequest = null;
        try {
            Purchase fetchedRequest = get(id);
            updatedRequest = getUpdatedRequest(fetchedRequest, purchase);
            mongoBroker.getTemplate().save(updatedRequest);
        } catch(Exception exception) {
            System.out.println("Exception [" + exception.getMessage() + "]");
            throw new WebServiceException(500, exception.getMessage());
        }
        return updatedRequest;
    }
    
    public Purchase delete(String id, Purchase purchase) throws WebServiceException 
    {
    	 try {
    		 mongoBroker.getTemplate().remove(get(id));
         } catch(Exception exception) {
             System.out.println("Exception [" + exception.getMessage() + "]");
             throw new WebServiceException(500, exception.getMessage());
         }
    	 return new Purchase();
    }

    public Purchase get(String id) throws WebServiceException {
        System.out.println("AuthorizationRequestDAO.get");
        Purchase response = null;
        try {
            response = mongoBroker.getTemplate().findById(id, Purchase.class);
        } catch(Exception exception) {
            System.out.println("Exception [" + exception.getMessage() + "]");
            throw new WebServiceException(500, exception.getMessage());
        }
        if(response == null) {
            response = new Purchase();
        }
        return response;
    }

    public List<Purchase> getAll() throws WebServiceException {
        System.out.println("AuthorizationRequestDAO.getAll");
        List<Purchase> response = null;
        try {
            response = mongoBroker.getTemplate().findAll(Purchase.class);
        } catch(Exception exception) {
            System.out.println("Exception [" + exception.getMessage() + "]");
            throw new WebServiceException(500, exception.getMessage());
        }
        if(response == null) {
            response = new ArrayList<Purchase>();
        }
        return response;
    }

    public List<Purchase> search(PurchaseSearchCriteria purchaseSearchCriteria) throws WebServiceException {
        System.out.println("AuthorizationRequestDAO.search");
        List<Purchase> response = null;
        try {
            Query query = getQueryFromSearchCriteria(purchaseSearchCriteria);
            response = mongoBroker.getTemplate().find(query, Purchase.class);
        } catch(Exception exception) {
            System.out.println("Exception [" + exception.getMessage() + "]");
            throw new WebServiceException(500, exception.getMessage());
        }
        if(response == null) {
            response = new ArrayList<Purchase>();
        }
        return response;
    }

    // TODO: Throw exceptions in error cases
    private Purchase getUpdatedRequest(Purchase fetchedRequest, Purchase updatedRequest) throws WebServiceException {
        updatedRequest.setId(fetchedRequest.getId());
        // TODO: Other checks go here. Only update the fields that need to be updated
        return updatedRequest;
    }

    private Query getQueryFromSearchCriteria(PurchaseSearchCriteria purchaseSearchCriteria) {
        System.out.println("AuthorizationRequestDAO.getQueryFromSearchCriteria => purchaseSearchCriteria [" + purchaseSearchCriteria.toString() + "]");

        Query query = new Query();
        if(!StringUtils.isEmpty(purchaseSearchCriteria.getUserIPAddress())) {
            query.addCriteria(Criteria.where("userIPAddress").is(purchaseSearchCriteria.getUserIPAddress()));
        }
        if(!StringUtils.isEmpty(purchaseSearchCriteria.getEmail())) {
            query.addCriteria(Criteria.where("email").is(purchaseSearchCriteria.getEmail()));
        }
        if(!StringUtils.isEmpty(purchaseSearchCriteria.getClientId())) {
            query.addCriteria(Criteria.where("clientId").is(purchaseSearchCriteria.getClientId()));
        }
        if(!StringUtils.isEmpty(purchaseSearchCriteria.getOrderNumber())) {
            query.addCriteria(Criteria.where("orderNumber").is(purchaseSearchCriteria.getOrderNumber()));
        }
        return query;
    }
}
