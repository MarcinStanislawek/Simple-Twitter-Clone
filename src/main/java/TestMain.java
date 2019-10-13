import dao.UserDao;
import hibernate.util.HibernateUtil;
import model.User;

import java.util.Date;

public class TestMain {
    public static void main(String[] args) {
        HibernateUtil hibernateUtil = HibernateUtil.getInstance();
        UserDao userDao = new UserDao();

        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setDateOfRegistration(new Date());
            user.setLogin("Login" + i);
            user.setName("Name" + i);
            user.setLastName("Lastname" + i);
            user.setPassword("pass");
            user.setEmail("asd" + i + "@wp.pl");
            userDao.saveUser(user);
        }
    }
}
