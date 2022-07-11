package ru.mypackage.rest;

import com.google.gson.Gson;
import ru.mypackage.dto.UserDto;
import ru.mypackage.repository.hibernate.HiberEventRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberUserRepositoryImpl;
import ru.mypackage.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/v1/users/*")
public class UserRestControllerV1 extends HttpServlet {

    private final UserService userService = new UserService(new HiberUserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] strArray = req.getRequestURI().split("/");
        if(strArray[strArray.length-1].equals("users")){
            List<UserDto> userEntities = userService.getAll();
            String jsonUsers = new Gson().toJson(userEntities);
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonUsers.getBytes());
            outputStream.flush();
        }else{
            String jsonUser = new Gson().toJson(userService.getById(getId(req)));
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonUser.getBytes());
            outputStream.flush();
        }
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonStr = req.getReader().lines().collect(Collectors.joining());
        UserDto userDto = new Gson().fromJson(jsonStr, UserDto.class);
        userService.save(userDto);
        resp.setStatus(201);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = getId(req);
        String jsonStr = req.getReader().lines().collect(Collectors.joining());
        UserDto userDto = new Gson().fromJson(jsonStr, UserDto.class);
        userDto.setId(id);
        userService.update(userDto);
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        userService.remove(getId(req));
        resp.setStatus(200);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// private methods ///////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Long getId(HttpServletRequest req){
        String[] strArray = req.getRequestURI().split("/");
        return Long.parseLong(strArray[strArray.length-1]);
    }
}
