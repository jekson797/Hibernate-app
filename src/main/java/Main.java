import manager.UsersManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    private static SessionFactory factory;

    public static void main(String[] args) {
        tryToCreateSessionFactory();
        UsersManager usersManager = new UsersManager(factory);
        usersManager.showAllUsers();
    }

    private static void tryToCreateSessionFactory() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Failed to create sessionFactory object. " + e);
        }
    }
}
