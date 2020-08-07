package manager;

import entity.Role;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UsersManager {

    private final SessionFactory factory;

    public UsersManager(SessionFactory factory) {
        this.factory = factory;
    }

    public void showAllUsers() {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<User> users = session.createQuery("FROM User").list();
            for (User user : users) {
                System.out.println(user);
            }

            transaction.commit();
        }
    }

    public void insertUser(User user) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Role role = new RolesManager(factory).selectRoleByName("Guest");
            user.addRole(role);
            session.save(user);

            transaction.commit();
        }
    }

    public User selectUserByUsername(String username) {
        User user = null;
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<User> users = session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username).list();
            if (users.size() > 0) {
                user = users.get(0);
            } else {
                System.err.println("User was not found");
            }

            transaction.commit();
        }
        return user;
    }

    public void deleteUser(User user) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.delete(user);

            transaction.commit();
        }
    }

    public void deleteUserByUsername(String username) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createNativeQuery("DELETE FROM Users WHERE username = :username")
                    .setParameter("username", username).executeUpdate();

            transaction.commit();
        }
    }

    public void addRole(User user, Role role) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            user.addRole(role);
            session.update(user);

            transaction.commit();
        }
    }

    public void deleteRole(User user, Role role) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            user.deleteRole(role);
            session.update(user);

            transaction.commit();
        }
    }
}
