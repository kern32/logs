package controller;

import service.LogManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LogOperationServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        String id = req.getParameter("id");
        String appName = req.getParameter("appName");
        String fileLink = req.getParameter("fileLink");
        String folderLink = req.getParameter("folderLink");

        switch (operation) {
            case ("add"):
                LogManager.addOperation(appName, fileLink, folderLink);
                break;
            case ("remove"):
                LogManager.deleteOperation(id, appName, fileLink);
                break;
        }
        res.sendRedirect("/logs");
    }
}

