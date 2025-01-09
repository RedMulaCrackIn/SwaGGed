<%@ page import="swagged.model.dao.PostDAO" %>
<%@ page import="swagged.model.dao.CommunityDAO" %>
<%@ page import="swagged.model.dao.UtenteDAO" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="swagged.model.bean.*" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    List<Bean> risultati = (List<Bean>) request.getSession().getAttribute("risultati");

    UtenteDAO utenteDAO = new UtenteDAO();
    UtenteBean utenteBean = null;

%>
