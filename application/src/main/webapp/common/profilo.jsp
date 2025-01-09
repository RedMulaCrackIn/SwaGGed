<%@ page import="swagged.model.bean.PostBean" %>
<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="java.util.List" %>
<%@ page import="swagged.model.bean.CommentoBean" %>
<%@ page import="swagged.model.bean.ApprezzaPostBean" %>
<%@ page import="swagged.model.dao.PostDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="swagged.model.dao.ApprezzaPostDAO" %>
<%@ page import="swagged.model.dao.CommentoDAO" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    PostDAO postDAO = new PostDAO();
    List<PostBean> postCreati = postDAO.getByEmail(utente.getEmail());
    ApprezzaPostDAO apprezzaPostDAO = new ApprezzaPostDAO();
    List<ApprezzaPostBean> apprezzaPostBeans = apprezzaPostDAO.getByEmail(utente.getEmail());
    CommentoDAO commentoDAO = new CommentoDAO();
    List<CommentoBean> commentiCreati = commentoDAO.getByUtenteEmail(utente.getEmail());
    List<PostBean> postApprezzati = new ArrayList<>();
    for (ApprezzaPostBean post : apprezzaPostBeans) {
        postApprezzati.add(postDAO.getById(post.getPostId()));
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
