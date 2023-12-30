package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String createUsersTable = "create table if not exists mydatabase.users (id bigint primary key auto_increment, name varchar(255), lastname varchar(255), age int)";
    private static final String dropUsersTable = "drop table if exists mydatabase.users";

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getConnection();
    }

    @Override
    public void createUsersTableQuery() {
        Transaction transaction = null;
        try (Session session = Util.getConnection().openSession()) {
            User user = new User();
            transaction = session.beginTransaction();
            session.createNativeQuery(String.format(createUsersTable, user.getClass().getSimpleName())).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(dropUsersTable).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getConnection().openSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getConnection().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Override
    public List < User > getAllUsers() {
        List<User> result = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = Util.getConnection().openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            transaction = session.beginTransaction();
            result = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnection().openSession()) {
            transaction = session.beginTransaction();
            final List<User> deleted = session.createCriteria(User.class).list();
            for (Object o : deleted) {
                session.delete(o);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
