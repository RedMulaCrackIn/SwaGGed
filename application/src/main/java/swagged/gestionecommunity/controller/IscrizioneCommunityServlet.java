package swagged.gestionecommunity.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;

@WebServlet("/iscrizioneCommunity")
public class IscrizioneCommunityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestioneCommunityServiceImpl gestioneCommunity = new GestioneCommunityServiceImpl();

}
