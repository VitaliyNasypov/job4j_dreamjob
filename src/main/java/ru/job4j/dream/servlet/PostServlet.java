package ru.job4j.dream.servlet;

import ru.job4j.dream.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        Store.instOf().save(request.getParameter("name"), request.getParameter("description"));
        response.sendRedirect(request.getContextPath() + "/posts.jsp");
    }
}