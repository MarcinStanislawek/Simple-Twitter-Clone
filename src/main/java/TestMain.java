import hibernate.util.HibernateUtil;
import model.User;

public class TestMain {
    public static void main(String[] args) {
        HibernateUtil hibernateUtil = HibernateUtil.getInstance();
        User user = new User.UserBuilder()
                .buildName("Marcin")
                .buildLastName("Stanisławek")
                .buildLogin("Marcyn")
                .buildEmail("dupa@wp.pl")
                .buildPassword("pass")
                .buildUser();

        hibernateUtil.save(user);
    }
}
