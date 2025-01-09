package swagged.gestionecommenti.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import swagged.gestionecommenti.services.GestioneCommentiService;
import swagged.gestionecommenti.services.GestioneCommentiServiceImpl;

@WebServlet("/visualizzaCommento")
public class VisualizzaCommentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestioneCommentiService gestioneCommenti = new GestioneCommentiServiceImpl();

}
