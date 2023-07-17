package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory faktory = Util.getConnectionHibernate();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = null;
        try {session = faktory.openSession();
           session.beginTransaction();
           session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (id INT NOT NULL AUTO_INCREMENT, name" +
                   " VARCHAR(55), lastName VARCHAR(55), age INT(3), PRIMARY KEY (id))").executeUpdate();
       } catch (Exception e){
           e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {session = faktory.openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {session = faktory.openSession();
        session.beginTransaction();
        session.save(new User(name,lastName,age));
        session.getTransaction().commit();
       } catch (Exception e){
            session.getTransaction().rollback();
           e.printStackTrace();
       }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {session = faktory.openSession();
            session.beginTransaction();
            session.remove(session.get(User.class,id));
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Session session = faktory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User ", User.class).getResultList();

        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = faktory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM User ");
            query.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
