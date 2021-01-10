package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("term").matches("\\D+")) {
            String findElement = req.getParameter("term").substring(0, 1).toUpperCase()
                    + req.getParameter("term").substring(1);
            List<String> city = PsqlStore.instOf().findByCity(findElement);
            Gson gson = new Gson();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(gson.toJson(city.stream().limit(5).toArray()));
            resp.getWriter().flush();
        }
    }
}
