package ru.mypackage.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mypackage.model.File;
import ru.mypackage.repository.FileRepository;
import ru.mypackage.utils.GetSessionFactory;

import java.util.List;

public class HiberFileRepositoryImpl implements FileRepository {
    @Override
    public List<File> getAll() {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<File> files = session.createQuery("From File").list();
        transaction.commit();
        session.close();
        return files;
    }

    @Override
    public File getById(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        File file = session.get(File.class, id);
        transaction.commit();
        session.close();
        return file;
    }

    @Override
    public File save(File file) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(file);
        transaction.commit();
        session.close();
        return file;
    }

    @Override
    public File update(File file) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(file);
        transaction.commit();
        session.close();
        return file;
    }

    @Override
    public void remove(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        File file = session.get(File.class, id);
        session.delete(file);
        transaction.commit();
        session.close();
    }
}
