package ru.mypackage.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mypackage.model.*;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.utils.GetSessionFactory;

import java.util.List;

public class HiberEventRepositoryImpl implements EventRepository {
    @Override
    public List<Event> getAll() {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Event> events = session.createQuery("From Event").list();
        transaction.commit();
        session.close();
        return events;
    }

    @Override
    public Event getById(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Event event = (Event) session.createQuery("select e from Event e join fetch e.user joint fetch e.file where e.id =:id")
                .setParameter("id", id).uniqueResult();
        transaction.commit();
        session.close();
        return event;
    }

    @Override
    public Event save(Event event) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(event);
        transaction.commit();
        session.close();
        return event;
    }

    @Override
    public Event update(Event event) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(event);
        transaction.commit();
        session.close();
        return event;
    }

    @Override
    public void remove(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Event event = session.get(Event.class, id);
        session.delete(event);
        transaction.commit();
        session.close();
    }
}
