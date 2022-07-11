package ru.mypackage.rest;

import com.google.gson.Gson;
import ru.mypackage.dto.FileDto;
import ru.mypackage.dto.UserDto;
import ru.mypackage.repository.hibernate.HiberEventRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberFileRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberUserRepositoryImpl;
import ru.mypackage.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


@WebServlet("/v1/files/*")
@MultipartConfig
public class FileRestControllerV1 extends HttpServlet {

    private final FileService fileService = new FileService(
            new HiberFileRepositoryImpl(),
            new HiberEventRepositoryImpl());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] strArray = req.getRequestURI().split("/");
        if(strArray[strArray.length-1].equals("files")){
            List<FileDto> fileDtoList = fileService.getAll();
            String jsonFiles = new Gson().toJson(fileDtoList);
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonFiles.getBytes());
            outputStream.flush();
        }else{
            String jsonFile = new Gson().toJson(fileService.getById(getId(req)));
            resp.setContentType("application/json");
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(jsonFile.getBytes());
            outputStream.flush();
        }
        resp.setStatus(200);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = UserDto.builder()
                .id(Long.parseLong(req.getHeader("user_id")))
                .build();
        fileService.save(getFileDto(req), userDto);
        resp.setStatus(201);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // It is nothing to update in a file
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fileService.remove(getId(req));
        resp.setStatus(200);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// private methods ///////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private FileDto getFileDto(HttpServletRequest req) throws ServletException, IOException {
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

        return FileDto.builder()
                .location(location)
                .size(size)
                .build();
    }

    private Long getId(HttpServletRequest req){
        String[] strArray = req.getRequestURI().split("/");
        return Long.parseLong(strArray[strArray.length-1]);
    }

}
