package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class UploadServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServlet.class.getName());
    private static final Path CANDIDATE_DIR = Path.of(File.separator + "bin" + File.separator
            + "images" + File.separator + "photo_id");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(
                "/candidate/edit.jsp?id=" + req.getParameter("id"));
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        if (ServletFileUpload.isMultipartContent(req) && req.getContentLengthLong() < 1000000) {
            ServletFileUpload upload = new ServletFileUpload();
            try {
                FileItemIterator iter = upload.getItemIterator(req);
                while (iter.hasNext()) {
                    FileItemStream item = iter.next();
                    InputStream stream = item.openStream();
                    if (!item.isFormField()) {
                        if (!Files.exists(CANDIDATE_DIR)) {
                            Files.createDirectories(CANDIDATE_DIR);
                        }
                        if (item.getContentType().split("/")[0].equals("image")) {
                            String fileName = "temp_" + item.getName();
                            stream.transferTo(Files.newOutputStream(
                                    Path.of(CANDIDATE_DIR + File.separator + fileName)));
                            req.setAttribute("imageFile", fileName);
                        }
                    }
                }
            } catch (FileUploadException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        doGet(req, resp);
    }
}
