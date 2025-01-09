<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="swagged.model.bean.PostBean" %>
<%@ page import="java.util.List" %>
<%
    UtenteBean profilo = (UtenteBean) session.getAttribute("profilo");
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    List<PostBean> posts = (List<PostBean>) profilo.get("postCreati");
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SwaGGed | <%=profilo.getUsername()%>
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
<div id="loading">
    <div id="loading-center">
    </div>
</div>
    <%
    if (profilo.isBandito()) {
%>
<div class="wrapper">
    <jsp:include page="fragments/sidebar.jsp"/>
    <jsp:include page="fragments/navbar.jsp"/>
    <div class="container p-0">
        <div class="row no-gutters height-self-center">
            <div class="col-sm-12 text-center align-self-center">
                <div class="iq-error position-relative mt-5">
                    <h2 class="mb-0 text-center">L'utente &egrave; stato bandito.</h2>
                </div>
            </div>
        </div>
    </div>
</div>
    <%
} else {
%>
<!-- loader Start -->
<!-- loader END -->
<!-- Wrapper Start -->
<div class="wrapper">
    <jsp:include page="fragments/sidebar.jsp"/>
    <jsp:include page="fragments/navbar.jsp"/>
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
                                        <img src="<%=request.getContextPath() + "/assets/images/pfp/" + profilo.getImmagine()%>"
                                             alt="profile-img"
                                             class="avatar-130 img-fluid"/>
                                    </div>
                                    <div class="profile-detail">
                                        <h3 class=""><%=profilo.getUsername()%>
                                        </h3>
                                    </div>
                                </div>
                                <div class="profile-info p-3 d-flex align-items-center justify-content-between position-relative">
                                    <div class="social-info">
                                        <ul class="social-data-block d-flex align-items-center justify-content-between list-inline p-0 m-0">
                                            <li class="text-center ps-3">
                                                <h6>Post</h6>
                                                <p class="mb-0"><%=profilo.get("postCreati").size()%>
                                                </p>
                                            </li>
                                        </ul>
                                    </div>
                                        <%
                                        if (utente != null && utente.isAdmin()) {
                                    %>
                                    <div class="card-post-toolbar">
                                        <button type="button" class="btn bg-danger" data-bs-toggle="modal"
                                                data-bs-target="#exampleModal-ban">
                                            Ban
                                        </button>
                                    </div>
                                    <div class="modal fade" id="exampleModal-ban" tabindex="-1"
                                         aria-labelledby="exampleModalLabel" style="display: none;"
                                         aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel-ban">Confermare
                                                        ban?</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close">
                                                    </button>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">Annulla
                                                    </button>
                                                    <a href="<%=request.getContextPath()%>/utente?mode=ban&utenteEmail=<%=profilo.getEmail()%>">
                                                        <button type="button" class="btn btn-primary">Conferma
                                                        </button>
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
                        </div>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="timeline" role="tabpanel">
                            <div class="card-body p-0">
                                <div class="row">


                                        <%
                                        for (PostBean post : posts) {

                                    %>
                                    <div class="col-sm-12">
                                        <div class="card">
                                            <div class="card-body">
                                                <div class="user-post-data py-3">
                                                    <div class="d-flex justify-content-between">
                                                        <div class="me-3">
                                                            <img class="avatar-60 rounded-circle"
                                                                 src="<%=request.getContextPath() + "/assets/images/pfp/" + profilo.getImmagine()%>"
                                                                 alt="">
                                                        </div>
                                                        <div class="w-100">
                                                            <div class="d-flex justify-content-between">
                                                                <div class="">
                                                                    <h5 class="mb-0 d-inline-block">
                                                                        <a href="<%=request.getContextPath()%>/utente?mode=visualizza&username=<%=profilo.getUsername()%>"
                                                                           class=""><%=profilo.getUsername()%>
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
                                                    if (post.getImmagine() != null && !post.getImmagine().isEmpty()) {
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