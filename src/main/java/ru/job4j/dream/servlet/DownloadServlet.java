package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadServlet extends HttpServlet {
    private static final Path CANDIDATE_DIR = Path.of(File.separator + "bin" + File.separator
            + "images" + File.separator + "photo_id");
    private static final Path NO_PHOTO_ID = Path.of("no_photo.jpg");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String fileImageId = req.getParameter("image").isEmpty()
                ? NO_PHOTO_ID.toString()
                : req.getParameter("image");
        Path fileName = Path.of(CANDIDATE_DIR + File.separator + fileImageId);
        resp.setContentType("image/" + fileImageId.split("\\.")[1]);
        resp.setHeader("Content-Disposition", "attachment; filename=\""
                + fileName.toString() + "\"");
        resp.getOutputStream().write(Files.readAllBytes(fileName));
    }
}
