package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (PsqlStore.instOf().isUserCreated(email)) {
            req.setAttribute("error", "Данный пользователь существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else {
            String name = req.getParameter("name");
            String group = req.getParameter("group");
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setGroup(group);
            PsqlStore.instOf().save(user);
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
