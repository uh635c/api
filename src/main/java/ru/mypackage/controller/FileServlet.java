package ru.mypackage.controller;

import com.google.gson.Gson;
import ru.mypackage.model.File;
import ru.mypackage.model.User;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.repository.FileRepository;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.repository.hibernate.HiberEventRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberFileRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberUserRepositoryImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Collection;
import java.util.List;


@WebServlet("/v1/files/*")
@MultipartConfig
public class FileServlet extends HttpServlet {

    private FileRepository fileRepository = new HiberFileRepositoryImpl();
    private UserRepository userRepository = new HiberUserRepositoryImpl();
    private EventRepository eventRepository = new HiberEventRepositoryImpl();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] strArray = req.getRequestURI().split("/");
        if(strArray[strArray.length-1].equals("files")){
            List<File> files = fileRepository.getAll();
            String jsonFiles = new Gson().toJson(files);
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonFiles.getBytes());
            outputStream.flush();
        }else{
            String jsonFile = new Gson().toJson(fileRepository.getById(getId(req)));
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonFile.getBytes());
            outputStream.flush();
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fileRepository.save(getFile(req));

        User user = userRepository.getById(Long.parseLong(req.getParameter("user_id")));


        //create new Event and save it.

        resp.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = getFile(req);
        file.setId(getId(req));

        fileRepository.update(file);

        //create new Event and save it.

        //resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fileRepository.remove(getId(req));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// private methods ///////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private File getFile(HttpServletRequest req) throws ServletException, IOException {
        String destination = "D:\\Java\\temp";
        String location = null;
        long size = 0L;

        Collection<Part> parts = req.getParts();
        if(parts.size()==1){
            for(Part p : parts){
                size = p.getSize();
                location = destination + p.getSubmittedFileName();
            }
        }else{
            try {
                throw new Exception("Attached more than one file");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new File(location, size);
    }

    private Long getId(HttpServletRequest req){
        String[] strArray = req.getRequestURI().split("/");
        return Long.parseLong(strArray[strArray.length-1]);
    }

}
