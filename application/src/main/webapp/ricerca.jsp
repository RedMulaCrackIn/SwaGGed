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
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SwaGGed | Homepage</title>

    <link rel="shortcut icon" href="<%=request.getContextPath()%>/assets/images/favicon.ico"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/libs.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/socialv.css?v=4.0.0">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/vendor/@fortawesome/fontawesome-free/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/vendor/remixicon/fonts/remixicon.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/vendor/vanillajs-datepicker/dist/css/datepicker.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/vendor/font-awesome-line-awesome/css/all.min.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/vendor/line-awesome/dist/line-awesome/css/line-awesome.min.css">

</head>
<body class="  ">
<jsp:include page="fragments/sidebar.jsp"/>
<jsp:include page="fragments/navbar.jsp"/>
<div id="loading">
    <div id="loading-center">
    </div>
</div>
<div class="modal fade" id="post-modal-community" tabindex="-1" aria-labelledby="post-modalLabel"
     aria-hidden="true">
    <div class="modal-dialog   modal-fullscreen-sm-down">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="post-modalLabel-community">Crea Community</h5>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><i
                        class="ri-close-fill"></i></button>
            </div>
            <div class="modal-body">
                <form action="community" method="post" id="creaCommunity">
                    <input type="hidden" name="mode" value="create">
                    <div class="form-group">
                        <label class="form-label" for="communityNomeCreazione">Nome community</label>
                        <input type="text" class="form-control mb-0" id="communityNomeCreazione"
                               name="communityNomeCreazione"
                               placeholder="Inserisci il nome della community"
                               required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="descrizione">Descrizione</label>
                        <input type="text" class="form-control mb-0" id="descrizione"
                               name="descrizione"
                               placeholder="Inserisci descrizione">
                    </div>
                    <hr>
                    <button type="submit" class="btn btn-primary d-block w-100 mt-3">Crea</button>
                </form>
            </div>
        </div>
    </div>
</div>
