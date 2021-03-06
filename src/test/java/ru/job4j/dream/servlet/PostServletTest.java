package ru.job4j.dream.servlet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.User;
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
public class PostServletTest {

    @Test
    public void shouldAddNewPost() throws IOException {
        Store store = FakeMockStore.instOf();
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        httpSession.setAttribute("user", new User());
        Mockito.when(req.getParameter("id")).thenReturn("0");
        Mockito.when(req.getParameter("name")).thenReturn("Java");
        Mockito.when(req.getParameter("description")).thenReturn("Java Core");
        Mockito.when(req.getSession()).thenReturn(httpSession);
        new PostServlet().doPost(req, resp);
        Mockito.verify(resp).sendRedirect(req.getContextPath() + "/posts.do");
        Assert.assertEquals("Java",
                store.findByIdPost(4).getName());
    }

    @Test
    public void shouldForwardToPostsJsp() throws ServletException, IOException {
        Store store = FakeMockStore.instOf();
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(req.getRequestDispatcher("posts.jsp")).thenReturn(dispatcher);
        new PostServlet().doGet(req, resp);
        Mockito.verify(dispatcher).forward(req, resp);
    }

    @Test
    public void shouldSetAttributePosts() throws ServletException, IOException {
        Store store = FakeMockStore.instOf();
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(req.getRequestDispatcher("posts.jsp")).thenReturn(dispatcher);
        new PostServlet().doGet(req, resp);
        ArgumentCaptor<String> getAttribute = ArgumentCaptor.forClass(String.class);
        Mockito.verify(req).setAttribute(getAttribute.capture(), Mockito.anyCollection());
        Assert.assertEquals(getAttribute.getValue(), "posts");
    }
}
