package ru.mypackage;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mypackage.model.*;
import ru.mypackage.repository.hibernate.*;
import ru.mypackage.utils.GetSessionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application {
    public static void main(String[] args) {
//        Session session = GetSessionFactory.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        Test test = new Test(new Date());
//        session.save(test);
//        transaction.commit();
//        session.close();

        Session session = GetSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Test test1 = session.get(Test.class, 5);
        transaction.commit();
        session.close();

        System.out.println(test1.getDate());
        System.out.println(test1.getDate().getTime());

    }
}
