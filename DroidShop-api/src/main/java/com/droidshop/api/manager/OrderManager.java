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

import com.droidshop.api.dao.OrderDAO;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.order.Order;

@Service
public class OrderManager {
    @Autowired
    OrderDAO orderDAO;

    @Transactional
    public Order add(Order order) throws Exception {
        System.out.println("OrderManager: add");
        validate(order, POST.class);
        Order newOrder = orderDAO.add(order);
        return newOrder;
    }

    @Transactional
    public Order update(Long orderID, Order order) throws Exception {
        System.out.println("OrderManager: update");
        validate(order, PUT.class);

        if(Long.valueOf(orderID) != Long.valueOf(order.getId())) {
            System.out.println("Order ID from URI [" + orderID + "], Order ID from JSON [" +  order.getId() + "]");
            throw new Exception("Order ID Mismatch In Update Operation");
        }

        Order updatedOrder = orderDAO.update(orderID, order);
        return updatedOrder;
    }
    
    @Transactional
    public Order fetch(Long orderID) {
        System.out.println("OrderManager: fetch");
        Order fetchedOrder = orderDAO.fetch(orderID);
        return fetchedOrder;
    }

    @Transactional
    public Order fetchByUserName(String userName) {
        System.out.println("OrderManager: fetchByUserName");
        Order fetchedOrder = orderDAO.fetchByUserName(userName);
        return fetchedOrder;
    }

    @Transactional
    public List<Order> fetchAll(boolean includeAll) {
        System.out.println("OrderManager: fetchAll");
        List<Order> fetchedOrders = orderDAO.fetchAll(includeAll);
        return fetchedOrders;
    }

    @Transactional
    public Order delete(Long orderID, Order order) throws Exception {
        System.out.println("OrderManager: delete");
        validate(order, DELETE.class);

        if(Long.valueOf(orderID) != Long.valueOf(order.getId())) {
            System.out.println("Order ID from URI [" + orderID + "], Order ID from JSON [" +  order.getId() + "]");
            throw new Exception("Order ID Mismatch In Delete Operation");
        }

        Order deletedOrder = orderDAO.delete(orderID);
        return deletedOrder;
    }

    private <T> void validate(Order request, Class<T> T) throws WebServiceException {
        System.out.println("OrderManager.validate");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Order>> violationSet = validator.validate(request, T);
        if(violationSet.size() > 0) {
            List<String> violationMessageList = new ArrayList<String>();
            for(ConstraintViolation<Order> violation : violationSet) {
                System.out.println("Violation [" + violation.getMessage() + "]");
                violationMessageList.add(violation.getMessage());
            }
            throw new WebServiceException(400, "Bad Input Request", violationMessageList);
        }
    }
}
