package ru.job4j.dream.servlet;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;
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
@PrepareForTest({PsqlStore.class, LoggerFactory.class})
public class AuthServletTest {

    @Test
    public void shouldSuccessfullyAuthorizedUser() throws ServletException, IOException {
        Store store = FakeMockStore.instOf();
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        Mockito.when(req.getParameter("email")).thenReturn("test@test.ru");
        Mockito.when(req.getParameter("password")).thenReturn("test");
        Mockito.when(req.getSession()).thenReturn(httpSession);
        new AuthServlet().doPost(req, resp);
        Mockito.verify(resp).sendRedirect(req.getContextPath() + "/posts.do");
    }

    @Test
    public void shouldFailAuthorizedUser() throws ServletException, IOException {
        Store store = FakeMockStore.instOf();
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(req.getParameter("email")).thenReturn("fail@test.ru");
        Mockito.when(req.getParameter("password")).thenReturn("test");
        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(req.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
        new AuthServlet().doPost(req, resp);
        Mockito.verify(dispatcher).forward(req, resp);
    }
}
