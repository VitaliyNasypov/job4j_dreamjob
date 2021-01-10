package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String city = request.getParameter("city");
        int age = Integer.parseInt(request.getParameter("age"));
        Candidate candidate = new Candidate(id, firstName, lastName, age);
        if (!request.getParameter("imageId").isEmpty()) {
            candidate.setIdPhoto(request.getParameter("imageId"));
        }
        candidate.setCity(city);
        PsqlStore.instOf().save(candidate, (User) request.getSession().getAttribute("user"));
        response.sendRedirect(request.getContextPath() + "/candidates.do");
    }
}
