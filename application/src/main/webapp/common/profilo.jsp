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
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SwaGGed | <%=utente.getUsername()%>
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
<!-- Wrapper Start -->
<div id="loading">
    <div id="loading-center">
    </div>
</div>
<div class="wrapper">
    <jsp:include page="../fragments/sidebar.jsp"/>
    <jsp:include page="../fragments/navbar.jsp"/>
    <div id="content-page" class="content-page">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="card">
                        <div class="card-body profile-page p-0">
                            <div class="profile-header">
                                <div class="position-relative">
                                    <img src="<%=request.getContextPath()%>/assets/images/trasparente.png"
                                         alt="profile-bg"
                                         class="rounded img-fluid">
                                </div>
                                <div class="user-detail text-center mb-3">
                                    <div class="profile-img">
                                        <img src="<%=request.getContextPath() + "/assets/images/pfp/" + utente.getImmagine()%>"
                                             alt="profile-img"
                                             class="avatar-130 img-fluid"/>
                                    </div>
                                    <div class="profile-detail">
                                        <h3 class=""><%=utente.getUsername()%>
                                        </h3>
                                    </div>
                                </div>
                                <div class="profile-info p-3 d-flex align-items-center justify-content-between position-relative">
                                    <div class="social-info">
                                        <ul class="social-data-block d-flex align-items-center justify-content-between list-inline p-0 m-0">
                                            <li class="text-center ps-3">
                                                <h6>Post</h6>
                                                <p class="mb-0"><%=utente.get("postCreati").size()%>
                                                </p>
                                            </li>
                                        </ul>
                                    </div>
                                    <form class="" data-bs-toggle="modal"
                                          data-bs-target="#immagine-modal"
                                          action="javascript:void();">
                                        <a href="#immagine-modal"><i class="ri-pencil-line"></i></a>
                                    </form>
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
                            <div class="modal fade" id="immagine-modal" tabindex="-1" aria-labelledby="post-modalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog   modal-fullscreen-sm-down">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="immagine-modalLabel">Modifica immagine di
                                                profilo</h5>
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><i
                                                    class="ri-close-fill"></i></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="../utente" method="post" enctype="multipart/form-data">
                                                <input type="hidden" name="mode" value="modificaImmagine">
                                                <div class="form-group">
                                                    <label for="immagine" class="form-label custom-file-input">Inserisci
                                                        immagine</label>
                                                    <input class="form-control" type="file" id="immagine"
                                                           name="immagine">
                                                </div>
                                                <hr>
                                                <button type="submit" class="btn btn-primary d-block w-100 mt-3">Carica
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body p-0">
                            <div class="user-tabing">
                                <ul class="nav nav-pills d-flex align-items-center justify-content-center profile-feed-items p-0 m-0">
                                    <li class="nav-item col-12 col-sm-3 p-0">
                                        <a class="nav-link active" href="#pills-timeline-tab" data-bs-toggle="pill"
                                           data-bs-target="#timeline" role="button">Post Creati</a>
                                    </li>
                                    <li class="nav-item col-12 col-sm-3 p-0">
                                        <a class="nav-link" href="#pills-about-tab" data-bs-toggle="pill"
                                           data-bs-target="#about" role="button">Post Apprezzati</a>
                                    </li>
                                    <li class="nav-item col-12 col-sm-3 p-0">
                                        <a class="nav-link" href="#pills-friends-tab" data-bs-toggle="pill"
                                           data-bs-target="#friends" role="button">Commenti Creati</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="timeline" role="tabpanel">
                            <div class="card-body p-0">
                                <div class="row">
                                    <%
                                        for (PostBean post : postCreati) {

                                    %>
                                    <div class="col-sm-12">
                                        <div class="card">
                                            <div class="card-body">
                                                <div class="user-post-data py-3">
                                                    <div class="d-flex justify-content-between">
                                                        <div class="me-3">
                                                            <img class="avatar-60 rounded-circle"
                                                                 src="<%=request.getContextPath() + "/assets/images/pfp/" + utente.getImmagine()%>"
                                                                 alt="">
                                                        </div>
                                                        <div class="w-100">
                                                            <div class="d-flex justify-content-between">
                                                                <div class="">
                                                                    <h5 class="mb-0 d-inline-block">
                                                                        <a href="<%=request.getContextPath()%>/utente?mode=visualizza&username=<%=utente.getUsername()%>"
                                                                           class=""><%=utente.getUsername()%>
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
                                                    if (post.getCorpo().length() > 0) {
                                                %>
                                                <div class="user-post">
                                                    <p><%=post.getCorpo()%>
                                                    </p>
                                                </div>
                                                <%
                                                    }
                                                    if (post.getImmagine() != null && post.getImmagine().length() > 0) {
                                                %>
                                                <div class="user-post">
                                                    <img src="<%=request.getContextPath() + "/assets/images/post/" + post.getImmagine()%>"
                                                         alt="post-image" class="img-fluid w-100"/>
                                                </div>
                                                <%
                                                    }
                                                %>
                                                <div class="comment-area mt-3">
                                                    <div class="d-flex justify-content-between align-items-center flex-wrap">
                                                        <div class="like-block position-relative d-flex align-items-center">
                                                            <div class="d-flex align-items-center">
                                                                <div class="like-data">
                                                                    <%
                                                                        if (utente != null && utente.get("postApprezzati").contains(post)) {
                                                                    %>
                                                                    <a href="<%=request.getContextPath()%>/post?mode=like&id=<%=post.getId()%>">
                                                                        <i class="fa fa-thumbs-up"
                                                                           style="color: #50b5ff"></i>
                                                                    </a>
                                                                    <%
                                                                    } else if (utente != null && !utente.get("postApprezzati").contains(post)) {
                                                                    %>
                                                                    <a href="<%=request.getContextPath()%>/post?mode=like&id=<%=post.getId()%>">
                                                                        <i class="fa fa-thumbs-up"
                                                                           style="color: #777d74"></i>
                                                                    </a>
                                                                    <%
                                                                    } else {
                                                                    %>
                                                                    <i class="fa fa-thumbs-up"
                                                                       style="color: #777d74"></i>
                                                                    <%
                                                                        }
                                                                    %>
                                                                </div>
                                                                <div class="total-like-block ms-2 me-3">
                                                                    <label><%=post.getLikes()%>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <div class="like-data">
                                                                <a href="<%=request.getContextPath()%>/post?mode=visualizza&id=<%=post.getId()%>">
                                                                    <i class="far fa-comments"></i>
                                                                </a>
                                                            </div>
                                                            <div class="total-like-block ms-2 me-3">
                                                                <a href="<%=request.getContextPath()%>/post?mode=visualizza&id=<%=post.getId()%>">
                                                                    <label><%=post.getNumeroCommenti()%>
                                                                    </label>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <%
                                        }
                                    %>


                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="about" role="tabpanel">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <%
                                            for (PostBean post : postApprezzati) {
                                        %>
                                        <div class="col-sm-12">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="user-post-data py-3">
                                                        <div class="d-flex justify-content-between">
                                                            <div class="me-3">
                                                                <img class="avatar-60 rounded-circle"
                                                                     src="<%=request.getContextPath() + "/assets/images/pfp/" + utente.getImmagine()%>"
                                                                     alt="">
                                                            </div>
                                                            <div class="w-100">
                                                                <div class="d-flex justify-content-between">
                                                                    <div class="">
                                                                        <h5 class="mb-0 d-inline-block">
                                                                            <a href="<%=request.getContextPath()%>/utente?mode=visualizza&username=<%=utente.getUsername()%>"
                                                                               class=""><%=utente.getUsername()%>
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
                                                                <button type="button" class="btn btn-link mb-1" data-bs-toggle="modal" data-bs-target="#exampleModal-apprezzati">
                                                                    <i class="ri-delete-bin-7-line h4"></i>
                                                                </button>
                                                            </div>
                                                            <div class="modal fade" id="exampleModal-apprezzati" tabindex="-1" aria-labelledby="exampleModalLabel" style="display: none;" aria-hidden="true">
                                                                <div class="modal-dialog" role="document">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h5 class="modal-title" id="exampleModalLabel-apprezzati">Confermare eliminazione?</h5>
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
                                                        if (post.getCorpo().length() > 0) {
                                                    %>
                                                    <div class="user-post">
                                                        <p><%=post.getCorpo()%>
                                                        </p>
                                                    </div>
                                                    <%
                                                        }
                                                        if (post.getImmagine() != null) {
                                                    %>
                                                    <div class="user-post">
                                                        <img src="<%=request.getContextPath() + "/assets/images/post/" + post.getImmagine()%>"
                                                             alt="post-image" class="img-fluid w-100"/>
                                                    </div>
                                                    <%
                                                        }
                                                    %>
                                                    <div class="comment-area mt-3">
                                                        <div class="d-flex justify-content-between align-items-center flex-wrap">
                                                            <div class="like-block position-relative d-flex align-items-center">
                                                                <div class="d-flex align-items-center">
                                                                    <div class="like-data">
                                                                        <%
                                                                            if (utente != null && utente.get("postApprezzati").contains(post)) {
                                                                        %>
                                                                        <a href="<%=request.getContextPath()%>/post?mode=like&id=<%=post.getId()%>">
                                                                            <i class="fa fa-thumbs-up"
                                                                               style="color: #50b5ff"></i>
                                                                        </a>
                                                                        <%
                                                                        } else if (utente != null && !utente.get("postApprezzati").contains(post)) {
                                                                        %>
                                                                        <a href="<%=request.getContextPath()%>/post?mode=like&id=<%=post.getId()%>">
                                                                            <i class="fa fa-thumbs-up"
                                                                               style="color: #777d74"></i>
                                                                        </a>
                                                                        <%
                                                                        } else {
                                                                        %>
                                                                        <i class="fa fa-thumbs-up"
                                                                           style="color: #777d74"></i>
                                                                        <%
                                                                            }
                                                                        %>
                                                                    </div>
                                                                    <div class="total-like-block ms-2 me-3">
                                                                        <label><%=post.getLikes()%>
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                                <div class="like-data">
                                                                    <a href="<%=request.getContextPath()%>/post?mode=visualizza&id=<%=post.getId()%>">
                                                                        <i class="far fa-comments"></i>
                                                                    </a>
                                                                </div>
                                                                <div class="total-like-block ms-2 me-3">
                                                                    <a href="<%=request.getContextPath()%>/post?mode=visualizza&id=<%=post.getId()%>">
                                                                        <label><%=post.getNumeroCommenti()%>
                                                                        </label>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <%
                                            }

                                        %>


                                    </div>
                                </div>
                            </div>
                        </div>

</body>
</html>
