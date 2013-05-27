package com.droidshop.api.dao;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.droidshop.api.model.user.User;
import com.droidshop.api.model.user.UserStatus;
import com.droidshop.api.util.PasswordEncoder;

@Component
public class UserDAO extends AbstractDAO<User> {
	
	public UserDAO()
	{
		super(User.class);
	}
	
    public User add(User user) throws NoSuchAlgorithmException {
        System.out.println("UserDAO: add");
        System.out.println("UserDAO: START - adding user to the database");

        user.setStatus(UserStatus.PENDING);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        beginTransaction();
        user.setPassword(PasswordEncoder.getEncodedPassword(user.getUserName(), user.getPassword()));
        save(user);
        commitAndCloseTransaction();
        
        System.out.println("UserDAO: END - adding user to the database");

        return user;
    }

    public User update(Long userID, User user) throws NoSuchAlgorithmException {
        System.out.println("UserDAO: update");
        System.out.println("UserDAO: START - updating user to the database");
     
        beginTransaction();
        User fetchedUser = fetch(userID);

        if(user.getFirstName() != null && !fetchedUser.getFirstName().equals(user.getFirstName())) {
            fetchedUser.setFirstName(user.getFirstName());
        }
        if(user.getMiddleName() != null && !fetchedUser.getMiddleName().equals(user.getMiddleName())) {
            fetchedUser.setMiddleName(user.getMiddleName());
        }
        if(user.getLastName() != null && !fetchedUser.getLastName().equals(user.getLastName())) {
            fetchedUser.setLastName(user.getLastName());
        }
        String currentEncodedPassword = PasswordEncoder.getEncodedPassword(user.getUserName(), user.getPassword());
        if(user.getPassword() != null && !fetchedUser.getPassword().equals(currentEncodedPassword)) {
            fetchedUser.setPassword(currentEncodedPassword);
        }
        if(user.getStatus() != null && fetchedUser.getStatus() != user.getStatus()) {
            fetchedUser.setStatus(user.getStatus());
        }
        if(user.getDateOfBirth() != null && fetchedUser.getDateOfBirth() != user.getDateOfBirth()) {
            fetchedUser.setDateOfBirth(user.getDateOfBirth());
        }
        fetchedUser.setUpdatedAt(new Date());

        update(fetchedUser);
        commitAndCloseTransaction();
        System.out.println("UserDAO: END - updating user to the database");

        return fetchedUser;
    }

    public User fetch(Long userID) {
        System.out.println("UserDAO: fetch");
        System.out.println("UserDAO: START - fetching user from the database by user ID");
        
        User fetchedUser = find(userID);
        System.out.println("UserDAO: END - fetching user from the database by user ID");
        
        return fetchedUser;
    }

    public User fetchByUsername(String username) {
        System.out.println("UserDAO: fetchByUsername");
        System.out.println("UserDAO: START - fetching user from the database by username");
        
        beginTransaction();
        User fetchedUser = (User) session.createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
        System.out.println("UserDAO: END - fetching user from the database by username");
        closeTransaction();
        
        return fetchedUser;
    }

    public List<User> fetchAll(boolean includeAll) {
        System.out.println("UserDAO: fetchAll");
        System.out.println("UserDAO: START - fetching all users from the database");
        
        beginTransaction();
        List<User> fetchedUsers = getAll();

        System.out.println("DEBUG: includeAll [" + includeAll + "]");
        if(includeAll == false) {
            System.out.println("UserDAO: removing non-active users");
            Iterator<User> iterator = fetchedUsers.iterator();
            while(iterator.hasNext()) {
                User currentUser = (User) iterator.next();
                String userStatusCode = currentUser.getStatusCode();
                System.out.println("DEBUG: User Status Code [" + userStatusCode + "]");
                if(!userStatusCode.equals("A")) {
                    System.out.println("DEBUG: Removing User [" + currentUser.getUserName() + "]");
                    iterator.remove();
                }
            }
        } else {
            System.out.println("UserDAO: including all users");
        }
        System.out.println("UserDAO: END - fetching all users from the database");

        return fetchedUsers;
    }

    public User delete(Long userID) {
        System.out.println("UserDAO: delete");
        System.out.println("UserDAO: START - setting user status to delete in the database");
        
        beginTransaction();
        User fetchedUser = fetch(userID);
        fetchedUser.setStatus(UserStatus.DELETED);
        fetchedUser.setUpdatedAt(new Date());
        update(fetchedUser);
        commitAndCloseTransaction();
        System.out.println("UserDAO: END - setting user status to delete in the database");

        return fetchedUser;
    }
}
