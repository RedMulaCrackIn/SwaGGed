package swagged.gestionepost.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import swagged.gestionepost.services.GestionePostServiceImpl;

@WebServlet("/cercaPost")
public class CercaPostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestionePostServiceImpl gestionePost = new GestionePostServiceImpl();

}
