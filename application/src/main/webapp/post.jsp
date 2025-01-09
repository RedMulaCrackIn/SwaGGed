<%@ page import="swagged.model.bean.PostBean" %>
<%@ page import="swagged.model.dao.UtenteDAO" %>
<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="swagged.model.bean.CommentoBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    PostBean post = (PostBean) session.getAttribute("post");
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SwaGGed | <%=post.getTitolo()%>
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
            <div class="row row-eq-height">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-12">

                            <div class="card iq-border-radius-20">


                                <div class="card-body">
                                    <div class="user-post-data py-3">
                                        <div class="d-flex justify-content-between">
                                                <%
                                                UtenteDAO utenteDAO = new UtenteDAO();
                                                UtenteBean utenteBean = new UtenteBean();
                                                utenteBean = utenteDAO.getByEmail(post.getUtenteEmail());
                                            %>
                                            <div class=" me-3">
                                                <img src="<%=request.getContextPath() + "/assets/images/pfp/" + utenteBean.getImmagine()%>"
                                                     alt="" class="avatar-60 rounded-circle">
                                            </div>
                                            <div class="w-100">
                                                <div class="d-flex justify-content-between">
                                                    <div class="">
                                                        <h5 class="mb-0 d-inline-block">
                                                            <a href="<%=request.getContextPath()%>/utente?mode=visualizza&username=<%=utenteBean.getUsername()%>"
                                                               class="">
                                                                <%=utenteBean.getUsername()%>
                                                            </a>
                                                        </h5>
                                                        <a href="<%=request.getContextPath()%>/community?mode=visualizza&nome=<%=post.getCommunityNome()%>">
                                                            <p class="mb-0"><%=post.getCommunityNome()%>
                                                            </p>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                                        <%
                                                if (utente != null && (post.getUtenteEmail().equals(utente.getEmail()) || utente.isAdmin())) {
                                            %>
                                                    <div class="card-post-toolbar">
                                                        <button type="button" class="btn btn-link mb-1" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                                            <i class="ri-delete-bin-7-line h4"></i>
                                                        </button>
                                                    </div>
                                                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" style="display: none;" aria-hidden="true">
                                                        <div class="modal-dialog" role="document">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel">Confermare eliminazione?</h5>
                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">

                                                                    </button>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
                                                                    <a href="<%=request.getContextPath()%>/post?mode=remove&id=<%=post.getId()%>">
                                                                        <button type="button" class="btn btn-primary">Conferma</button>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%
                                                        }
                                                    %>
                                        </div>
                                    </div>
                                    <div class="user-post">
                                        <h4 class="card-title"><%=post.getTitolo()%>
                                        </h4>
                                    </div>
                                        <%
                                        if (post.getCorpo() != null) {
                                    %>
                                    <div class="user-post">
                                        <p><%=post.getCorpo()%>
                                        </p>
                                    </div>
                                        <%
                                        }
                                        if (post.getImmagine() != null && !post.getImmagine().isEmpty()) {
                                    %>
                                    <div class="user-post">
                                        <img src="<%=request.getContextPath() + "/assets/images/post/" + post.getImmagine()%>"
                                             alt="post-image" class="img-fluid w-100"/>
                                    </div>
                                    <%
                                        }
                                    %>