<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="swagged.model.bean.PostBean" %>
<%@ page import="java.util.List" %>
<%
    UtenteBean profilo = (UtenteBean) session.getAttribute("profilo");
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    List<PostBean> posts = (List<PostBean>) profilo.get("postCreati");
%>

