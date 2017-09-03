package controller;

import service.LogManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ViewLogsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            File folder = new File(LogManager.getLogFileLocation(req.getParameter("id"), req.getParameter("file")));
            if (folder.isFile()) {
                InputStream is = new FileInputStream(folder);
                org.apache.commons.io.IOUtils.copy(is, res.getOutputStream());
                res.flushBuffer();
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
        req.getRequestDispatcher("/index.jsp").forward(req, res);
    }
}
