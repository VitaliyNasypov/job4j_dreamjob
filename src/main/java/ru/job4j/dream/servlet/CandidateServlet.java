package ru.job4j.dream.servlet;

import ru.job4j.dream.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        Store.instOf().saveCandidate(request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("age"));
        response.sendRedirect(request.getContextPath() + "/candidates.jsp");
    }
}
