<%@ page import="swagged.model.bean.CommunityBean" %>
<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="swagged.model.dao.UtenteDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="swagged.model.bean.PostBean" %>
<%@ page import="swagged.model.bean.IscrivitiCommunityBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    CommunityBean community = (CommunityBean) session.getAttribute("community");
%>

<!doctype html>
<html lang="en">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SwaGGed | <%=community.getNome()%>
    </title>

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
<!-- loader Start -->
<div id="loading">
    <div id="loading-center">
    </div>
</div>
<!-- loader END -->
<!-- Wrapper Start -->
<div class="wrapper">
    <jsp:include page="fragments/sidebar.jsp"/>
    <jsp:include page="fragments/navbar.jsp"/>
    <div id="content-page" class="content-page">
        <div class="container">
            <div class="row">

                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-body profile-page p-0">
                            <div class="profile-header">
                                <div class="position-relative">
                                    <img src="<%=request.getContextPath()%>/assets/images/trasparente.png"
                                         alt="profile-bg" class="rounded img-fluid">
                                </div>
                                <div class="user-detail text-center mb-3">
                                    <div class="profile-detail">
                                        <h3 class=""><%=community.getNome()%>
                                        </h3>
                                    </div>
                                    <%
                                        if (community.getDescrizione() != null && community.getDescrizione().length() > 0) {
                                    %>
                                    <span><%=community.getDescrizione()%></span>
                                    <%
                                        }
                                    %>
                                </div>
                                <div class="profile-info p-3 d-flex align-items-center justify-content-between position-relative">
                                    <div class="social-info">
                                        <ul class="social-data-block d-flex align-items-center justify-content-between list-inline p-0 m-0">
                                            <li class="text-center ps-3">
                                                <h6>Iscritti</h6>
                                                <p class="mb-0"><%=community.getIscritti()%>
                                                </p>
                                            </li>
                                        </ul>
                                    </div>
                                    <%
                                        if(utente != null){
                                    %>
                                    <form action="community" method="post">
                                        <input type="hidden" name="mode" value="iscrizione">
                                        <input type="hidden" name="nome" value="<%=community.getNome()%>">
                                        <%
                                            IscrivitiCommunityBean iscrivitiCommunityBean = new IscrivitiCommunityBean();
                                            iscrivitiCommunityBean.setCommunityNome(community.getNome());
                                            iscrivitiCommunityBean.setUtenteEmail(utente.getEmail());
                                            if (utente != null && !utente.get("communityIscritto").contains(iscrivitiCommunityBean)) {
                                        %>
                                        <button type="submit" class="btn btn-primary mb-2">Iscriviti</button>
                                        <%
                                        } else if (utente != null && utente.get("communityIscritto").contains(iscrivitiCommunityBean)) {
                                        %>
                                        <button type="submit" class="btn btn-primary mb-2">Disiscriviti</button>
                                        <%
                                            }
                                        %>
                                    </form>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                            <div class="modal fade" id="post-modal-community" tabindex="-1"
                                 aria-labelledby="post-modalLabel"
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
                                                    <label class="form-label" for="communityNomeCreazione">Nome
                                                        community</label>
                                                    <input type="text" class="form-control mb-0"
                                                           id="communityNomeCreazione"
                                                           name="communityNomeCreazione"
                                                           placeholder="Inserisci il nome della community"
                                                           required>
                                                    <p class="invalid-feedback" id="error-communityNomeCreazione"></p>
                                                </div>
                                                <div class="form-group">
                                                    <label class="form-label" for="descrizione">Descrizione</label>
                                                    <input type="text" class="form-control mb-0" id="descrizione"
                                                           name="descrizione"
                                                           placeholder="Inserisci descrizione">
                                                </div>
                                                <hr>
                                                <button type="submit" class="btn btn-primary d-block w-100 mt-3">Crea
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
</body>
</html>
