package ru.mypackage.utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/test")
@MultipartConfig
public class TestServletToDel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for(Part p : req.getParts()){
            System.out.println("Name - " + p.getName());
            System.out.println("ContentType - " + p.getContentType());
            System.out.println("SubmittedFileName - " + p.getSubmittedFileName());
            System.out.println("HeaderNames - "+ p.getHeaderNames());
            p.getHeaderNames().forEach(h-> System.out.println(p.getHeader(h)));
            System.out.println("Size - " + p.getSize());
        }
    }
}
