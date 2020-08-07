package manager;

import entity.Role;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RolesManager {

    private final SessionFactory factory;

    public RolesManager(SessionFactory factory) {
        this.factory = factory;
    }

    public void showAllRoles() {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<Role> roles = session.createQuery("FROM Role").list();
            for (Role role : roles) {
                System.out.println(role);
            }

            transaction.commit();
        }
    }

    public void insertRole(Role role) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(role);

            transaction.commit();
        }
    }

    public void deleteRole(Role role) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createQuery("DELETE FROM Role WHERE roleName = :role")
                    .setParameter("role", role.getRoleName()).executeUpdate();

            transaction.commit();
        }
    }

    public void updateRole(Role oldRole, Role newRole) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createQuery("UPDATE Role SET roleName = :newName WHERE roleName = :oldName")
                    .setParameter("newName", newRole.getRoleName())
                    .setParameter("oldName", oldRole.getRoleName()).executeUpdate();

            transaction.commit();
        }
    }

    public Role selectRoleByName(String roleName) {
        Role resultRole = null;
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<Role> roles = session.createQuery("FROM Role WHERE roleName = :role")
                    .setParameter("role", roleName.toLowerCase()).list();

            for (Role role : roles) {
                if (role.getRoleName().equals(roleName.toLowerCase())) {
                    resultRole = role;
                } else {
                    throw new HibernateException("Role not found");
                }
            }

            transaction.commit();
        }
        return resultRole;
    }
}
