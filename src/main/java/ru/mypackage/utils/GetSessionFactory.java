package ru.mypackage.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class GetSessionFactory {

    private volatile static SessionFactory sessionFactory;

    private GetSessionFactory() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (GetSessionFactory.class) {
                if (sessionFactory == null) {
                    new GetSessionFactory();
                }
            }
        }
        return sessionFactory;
    }

}
