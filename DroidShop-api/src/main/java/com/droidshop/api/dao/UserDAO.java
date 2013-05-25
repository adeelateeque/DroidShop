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

        user.setUserStatus(UserStatus.PENDING);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        beginTransaction();
        user.setPassword(PasswordEncoder.getEncodedPassword(user.getUsername(), user.getPassword()));
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

        if(user.getNameFirst() != null && !fetchedUser.getNameFirst().equals(user.getNameFirst())) {
            fetchedUser.setNameFirst(user.getNameFirst());
        }
        if(user.getNameMiddle() != null && !fetchedUser.getNameMiddle().equals(user.getNameMiddle())) {
            fetchedUser.setNameMiddle(user.getNameMiddle());
        }
        if(user.getNameLast() != null && !fetchedUser.getNameLast().equals(user.getNameLast())) {
            fetchedUser.setNameLast(user.getNameLast());
        }
        String currentEncodedPassword = PasswordEncoder.getEncodedPassword(user.getUsername(), user.getPassword());
        if(user.getPassword() != null && !fetchedUser.getPassword().equals(currentEncodedPassword)) {
            fetchedUser.setPassword(currentEncodedPassword);
        }
        if(user.getUserStatus() != null && fetchedUser.getUserStatus() != user.getUserStatus()) {
            fetchedUser.setUserStatus(user.getUserStatus());
        }
        if(user.getDateOfBirth() != null && fetchedUser.getDateOfBirth() != user.getDateOfBirth()) {
            fetchedUser.setDateOfBirth(user.getDateOfBirth());
        }
        if(user.getMonthOfBirth() != null && fetchedUser.getMonthOfBirth() != user.getMonthOfBirth()) {
            fetchedUser.setMonthOfBirth(user.getMonthOfBirth());
        }
        if(user.getYearOfBirth() != null && fetchedUser.getYearOfBirth() != user.getYearOfBirth()) {
            fetchedUser.setYearOfBirth(user.getYearOfBirth());
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
        
        beginTransaction();
        User fetchedUser = find(userID);
        System.out.println("UserDAO: END - fetching user from the database by user ID");
        closeTransaction();

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
                String userStatusCode = currentUser.getUserStatusCode();
                System.out.println("DEBUG: User Status Code [" + userStatusCode + "]");
                if(!userStatusCode.equals("A")) {
                    System.out.println("DEBUG: Removing User [" + currentUser.getUsername() + "]");
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
        fetchedUser.setUserStatus(UserStatus.DELETED);
        fetchedUser.setUpdatedAt(new Date());
        update(fetchedUser);
        commitAndCloseTransaction();
        System.out.println("UserDAO: END - setting user status to delete in the database");

        return fetchedUser;
    }
}
