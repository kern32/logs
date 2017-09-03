package controller;

import service.LogManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ListLogsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("columns", Arrays.asList(new String[]{"Link", "Application", "Last modified", "Remove"}));
        req.setAttribute("logList", LogManager.getLogsFromProperties());
        req.getRequestDispatcher("/index.jsp").forward(req, res);
    }
}
