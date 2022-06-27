package ru.mypackage.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mypackage.model.*;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.utils.GetSessionFactory;

import java.util.List;

public class HiberUserRepositoryImpl implements UserRepository {
    @Override
    public List<UserEntity> getAll() {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<UserEntity> userEntities = session
                .createQuery("from UserEntity").list();
        transaction.commit();
        session.close();
        return userEntities;
    }

    @Override
    public UserEntity getById(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = (UserEntity) session
                .createQuery("select u from UserEntity u left join fetch u.eventEntities where u.id = :id")
                .setParameter("id", id).uniqueResult();
        transaction.commit();
        session.close();
        return userEntity;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(userEntity);
        transaction.commit();
        session.close();
        return userEntity;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(userEntity);
        transaction.commit();
        session.close();
        return userEntity;
    }

    @Override
    public void remove(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = session.get(UserEntity.class, id);
        session.delete(userEntity);
        transaction.commit();
        session.close();
    }
}
