package ru.mypackage.repository.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mypackage.model.*;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.utils.GetSessionFactory;

import java.util.List;

public class HiberEventRepositoryImpl implements EventRepository {
    @Override
    public List<EventEntity> getAll() {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From EventEntity e left join fetch e.fileEntity" +
                " left join fetch e.userEntity");
        query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<EventEntity> eventEntities = query.list();
        transaction.commit();
        session.close();
        return eventEntities;
    }

    @Override
    public EventEntity getById(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        EventEntity eventEntity = (EventEntity) session
                .createQuery("select e from EventEntity e join fetch e.userEntity join fetch e.fileEntity where e.id =:id")
                .setParameter("id", id).uniqueResult();
        transaction.commit();
        session.close();
        return eventEntity;
    }

    @Override
    public EventEntity save(EventEntity eventEntity) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(eventEntity);
        transaction.commit();
        session.close();
        return eventEntity;
    }

    @Override
    public EventEntity update(EventEntity eventEntity) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(eventEntity);
        transaction.commit();
        session.close();
        return eventEntity;
    }

    @Override
    public void remove(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        EventEntity eventEntity = session.get(EventEntity.class, id);
        session.delete(eventEntity);
        transaction.commit();
        session.close();
    }
}
