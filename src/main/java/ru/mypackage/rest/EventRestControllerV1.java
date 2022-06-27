package ru.mypackage.rest;

import com.google.gson.Gson;
import ru.mypackage.dto.EventDto;
import ru.mypackage.repository.hibernate.HiberEventRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberFileRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberUserRepositoryImpl;
import ru.mypackage.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {

    private final EventService eventService = new EventService(
            new HiberEventRepositoryImpl(),
            new HiberUserRepositoryImpl(),
            new HiberFileRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] strArray = req.getRequestURI().split("/");
        if(strArray[strArray.length-1].equals("events")){
            List<EventDto> eventDtos = eventService.getAll();
            String jsonUsers = new Gson().toJson(eventDtos);
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonUsers.getBytes());
            outputStream.flush();
        }else{
            String jsonUser = new Gson().toJson(eventService.getById(getId(req)));
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
        EventDto eventDto = new Gson().fromJson(jsonStr, EventDto.class);
        eventService.save(eventDto);
        resp.setStatus(201);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = getId(req);
        String jsonStr = req.getReader().lines().collect(Collectors.joining());
        EventDto eventDto = new Gson().fromJson(jsonStr, EventDto.class);
        eventDto.setId(id);
        eventService.update(eventDto);
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        eventService.remove(getId(req));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// private methods ///////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Long getId(HttpServletRequest req){
        String[] strArray = req.getRequestURI().split("/");
        return Long.parseLong(strArray[strArray.length-1]);
    }
}
