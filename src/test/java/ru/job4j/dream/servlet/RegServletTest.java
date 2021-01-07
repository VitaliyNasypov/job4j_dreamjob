package ru.job4j.dream.servlet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.store.FakeMockStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class RegServletTest {
    @Test
    public void shouldAddNewUser() throws ServletException, IOException {
        Store store = FakeMockStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        Mockito.when(req.getParameter("id")).thenReturn("0");
        Mockito.when(req.getParameter("email")).thenReturn("admin@admin.ru");
        Mockito.when(req.getParameter("password")).thenReturn("admin");
        Mockito.when(req.getParameter("name")).thenReturn("Oleg");
        Mockito.when(req.getParameter("group")).thenReturn("administration");
        Mockito.when(req.getSession()).thenReturn(httpSession);
        new RegServlet().doPost(req, resp);
        Mockito.verify(resp).sendRedirect(req.getContextPath() + "/login.jsp");
        Assert.assertEquals("Oleg",
                store.findByUser("admin@admin.ru", "admin").getName());
    }

    @Test
    public void shouldNotAddNewUser() throws ServletException, IOException {
        Store store = FakeMockStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(req.getParameter("id")).thenReturn("0");
        Mockito.when(req.getParameter("email")).thenReturn("test@test.ru");
        Mockito.when(req.getParameter("password")).thenReturn("test");
        Mockito.when(req.getParameter("name")).thenReturn("Oleg");
        Mockito.when(req.getParameter("group")).thenReturn("administration");
        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(req.getRequestDispatcher("reg.jsp")).thenReturn(dispatcher);
        new RegServlet().doPost(req, resp);
        Mockito.verify(dispatcher).forward(req, resp);
    }
}
