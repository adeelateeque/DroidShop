package com.droidshop.api.manager;

import com.droidshop.api.dao.UserDAO;
import com.droidshop.api.model.error.WebServiceException;
import com.droidshop.api.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserManager {
    @Autowired
    UserDAO userDAO;

    public User add(User user) throws Exception {
        System.out.println("UserManager: add");
        validate(user, POST.class);
        User newUser = userDAO.add(user);
        return newUser;
    }

    public User update(Long userID, User user) throws Exception {
        System.out.println("UserManager: update");
        validate(user, PUT.class);

        if(Long.valueOf(userID) != Long.valueOf(user.getId())) {
            System.out.println("User ID from URI [" + userID + "], User ID from JSON [" +  user.getId() + "]");
            throw new Exception("User ID Mismatch In Update Operation");
        }

        User updatedUser = userDAO.update(userID, user);
        return updatedUser;
    }

    public User fetch(Long userID) {
        System.out.println("UserManager: fetch");
        User fetchedUser = userDAO.fetch(userID);
        return fetchedUser;
    }

    public User fetchByUsername(String username) {
        System.out.println("UserManager: fetchByUsername");
        User fetchedUser = userDAO.fetchByUsername(username);
        return fetchedUser;
    }

    public List<User> fetchAll(boolean includeAll) {
        System.out.println("UserManager: fetchAll");
        List<User> fetchedUsers = userDAO.fetchAll(includeAll);
        return fetchedUsers;
    }

    public User delete(Long userID, User user) throws Exception {
        System.out.println("UserManager: delete");
        validate(user, DELETE.class);

        if(Long.valueOf(userID) != Long.valueOf(user.getId())) {
            System.out.println("User ID from URI [" + userID + "], User ID from JSON [" +  user.getId() + "]");
            throw new Exception("User ID Mismatch In Delete Operation");
        }

        User deletedUser = userDAO.delete(userID);
        return deletedUser;
    }

    private <T> void validate(User request, Class<T> T) throws WebServiceException {
        System.out.println("UserManager.validate");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violationSet = validator.validate(request, T);
        if(violationSet.size() > 0) {
            List<String> violationMessageList = new ArrayList<String>();
            for(ConstraintViolation<User> violation : violationSet) {
                System.out.println("Violation [" + violation.getMessage() + "]");
                violationMessageList.add(violation.getMessage());
            }
            throw new WebServiceException(400, "Bad Input Request", violationMessageList);
        }
    }
}
