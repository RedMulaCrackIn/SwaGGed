package swagged.gestionepost.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import swagged.gestionepost.services.GestionePostServiceImpl;

@WebServlet("/creaPost")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class CreaPostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestionePostServiceImpl gestionePost = new GestionePostServiceImpl();

}
