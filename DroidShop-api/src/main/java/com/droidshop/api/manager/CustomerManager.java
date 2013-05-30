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

import com.droidshop.api.dao.CustomerDAO;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.user.Customer;

@Component
public class CustomerManager {
    @Autowired
    CustomerDAO customerDAO;

    public Customer add(Customer customer) throws Exception {
        System.out.println("CustomerManager: add");
        validate(customer, POST.class);
        Customer newCustomer = customerDAO.add(customer);
        return newCustomer;
    }

    public Customer update(Long customerID, Customer customer) throws Exception {
        System.out.println("CustomerManager: update");
        validate(customer, PUT.class);

        if(Long.valueOf(customerID) != Long.valueOf(customer.getId())) {
            System.out.println("Customer ID from URI [" + customerID + "], Customer ID from JSON [" +  customer.getId() + "]");
            throw new Exception("Customer ID Mismatch In Update Operation");
        }

        Customer updatedCustomer = customerDAO.update(customerID, customer);
        return updatedCustomer;
    }

    public Customer fetch(Long customerID) {
        System.out.println("CustomerManager: fetch");
        Customer fetchedCustomer = customerDAO.fetch(customerID);
        return fetchedCustomer;
    }

    public Customer fetchByUserName(String userName) {
        System.out.println("CustomerManager: fetchByUserName");
        Customer fetchedCustomer = customerDAO.fetchByUserName(userName);
        return fetchedCustomer;
    }

    public List<Customer> fetchAll(boolean includeAll) {
        System.out.println("CustomerManager: fetchAll");
        List<Customer> fetchedCustomers = customerDAO.fetchAll(includeAll);
        return fetchedCustomers;
    }

    public Customer delete(Long customerID, Customer customer) throws Exception {
        System.out.println("CustomerManager: delete");
        validate(customer, DELETE.class);

        if(Long.valueOf(customerID) != Long.valueOf(customer.getId())) {
            System.out.println("Customer ID from URI [" + customerID + "], Customer ID from JSON [" +  customer.getId() + "]");
            throw new Exception("Customer ID Mismatch In Delete Operation");
        }

        Customer deletedCustomer = customerDAO.delete(customerID);
        return deletedCustomer;
    }

    private <T> void validate(Customer request, Class<T> T) throws WebServiceException {
        System.out.println("CustomerManager.validate");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violationSet = validator.validate(request, T);
        if(violationSet.size() > 0) {
            List<String> violationMessageList = new ArrayList<String>();
            for(ConstraintViolation<Customer> violation : violationSet) {
                System.out.println("Violation [" + violation.getMessage() + "]");
                violationMessageList.add(violation.getMessage());
            }
            throw new WebServiceException(400, "Bad Input Request", violationMessageList);
        }
    }
}
