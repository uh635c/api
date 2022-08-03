package ru.mypackage;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mypackage.model.*;
import ru.mypackage.repository.hibernate.*;
import ru.mypackage.utils.GetSessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://eu-cdbr-west-03.cleardb.net/heroku_cb68caa524bfb98",
                    "bebab739e5ceda",
                    "a126cdd2");
            System.out.println(connection);
        }
        catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
