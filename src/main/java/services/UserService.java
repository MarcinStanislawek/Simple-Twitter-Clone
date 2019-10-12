package services;

import dao.UserDao;
import model.User;

import javax.persistence.NoResultException;

public class UserService {
    public static final String EMAIL_ERROR = "Email is already in use.";
    public static final String LOGIN_ERROR = "Login is already in use.";
    public static final String SUCCESS = "Success!";
    public static final String EMAIL_AND_LOGIN_ERROR = "Email and login are already in use.";

    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String registerUser(User user) {
        if (isUserEmailExist(user.getEmail()) && isUserLoginExist(user.getLogin())) {
            return EMAIL_AND_LOGIN_ERROR;
        } else if (isUserLoginExist(user.getLogin())) {
            return LOGIN_ERROR;
        } else if (isUserEmailExist(user.getEmail())) {
            return EMAIL_ERROR;
        } else {
            userDao.saveUser(user);
            return SUCCESS;
        }
    }

    private boolean isUserLoginExist(String login) {
        try {
            userDao.getUserByLogin(login);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    private boolean isUserEmailExist(String email) {
        try {
            userDao.getUserByEmail(email);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
