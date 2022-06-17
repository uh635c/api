package ru.mypackage.controller;


import com.google.gson.Gson;
import ru.mypackage.model.User;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.repository.hibernate.HiberUserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/v1/users/*")
@MultipartConfig
public class UserServlet extends HttpServlet {

    private UserRepository userRepository = new HiberUserRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] strArray = req.getRequestURI().split("/");
        if(strArray[strArray.length-1].equals("users")){
            List<User> users = userRepository.getAll();
            String jsonUsers = new Gson().toJson(users);
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonUsers.getBytes());
            outputStream.flush();
        }else{
            String jsonUser = new Gson().toJson(userRepository.getById(getId(req)));
            System.out.println(jsonUser);
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonUser.getBytes());
            outputStream.flush();
        }
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("name");
        User user = new User(userName, null);
        userRepository.save(user);
        //resp.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonStr = req.getReader().lines().collect(Collectors.joining());
        User user = new Gson().fromJson(jsonStr, User.class);
        userRepository.update(user);
        //resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userRepository.remove(getId(req));
        //resp.setStatus(200);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// private methods ///////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Long getId(HttpServletRequest req){
        String[] strArray = req.getRequestURI().split("/");
        return Long.parseLong(strArray[strArray.length-1]);
    }
}
