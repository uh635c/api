package ru.mypackage.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mypackage.model.FileEntity;
import ru.mypackage.repository.FileRepository;
import ru.mypackage.utils.GetSessionFactory;

import java.util.List;

public class HiberFileRepositoryImpl implements FileRepository {
    @Override
    public List<FileEntity> getAll() {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<FileEntity> fileEntities = session.createQuery("From FileEntity").list();
        transaction.commit();
        session.close();
        return fileEntities;
    }

    @Override
    public FileEntity getById(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        FileEntity fileEntity = session.get(FileEntity.class, id);
        transaction.commit();
        session.close();
        return fileEntity;
    }

    @Override
    public FileEntity save(FileEntity fileEntity) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(fileEntity);
        transaction.commit();
        session.close();
        return fileEntity;
    }

    @Override
    public FileEntity update(FileEntity fileEntity) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(fileEntity);
        transaction.commit();
        session.close();
        return fileEntity;
    }

    @Override
    public void remove(Long id) {
        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        FileEntity fileEntity = session.get(FileEntity.class, id);
        session.delete(fileEntity);
        transaction.commit();
        session.close();
    }
}
