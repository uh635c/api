package ru.mypackage.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mypackage.model.*;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.utils.GetSessionFactory;

import java.util.List;

public class HiberUserRepositoryImpl implements UserRepository {
    @Override
    public List<User> getAll() {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("select u from User u left join fetch u.events").list();
        transaction.commit();
        session.close();
        System.out.println(users);
        return users;
    }

    @Override
    public User getById(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.createQuery("select u from User u left join fetch u.events where u.id = :id")
                .setParameter("id", id).uniqueResult();
        transaction.commit();
        session.close();
        System.out.println(user);
        return user;
    }

    @Override
    public User save(User user) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public User update(User user) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public void remove(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }
}
