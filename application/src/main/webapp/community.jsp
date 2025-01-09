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
                                    <form action="iscrizioneCommunity" method="post">
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
                                            <form action="creaCommunity" method="post" id="creaCommunity">
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

                <div class="col-lg-8">
                    <%
                        if (utente != null) {
                    %>
                    <div id="post-modal-data" class="card">
                        <div class="card-header d-flex justify-content-between">
                            <div class="header-title">
                                <h4 class="card-title">Crea Post</h4>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="d-flex align-items-center">
                                <div class="user-img">
                                    <img src="<%=request.getContextPath() + "/assets/images/pfp/" + utente.getImmagine()%>"
                                         alt="userimg"
                                         class="avatar-60 rounded-circle">
                                </div>
                                <form class="post-text ms-3 w-100 " data-bs-toggle="modal" data-bs-target="#post-modal"
                                      action="javascript:void();">
                                    <input type="text" class="form-control rounded"
                                           placeholder="Scrivi qualcosa" style="border:none;">
                                </form>
                            </div>
                            <hr>
                        </div>
                        <div class="modal fade" id="post-modal" tabindex="-1" aria-labelledby="post-modalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog   modal-fullscreen-sm-down">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="post-modalLabel">Crea Post</h5>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><i
                                                class="ri-close-fill"></i></button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="creaPost" method="post" enctype="multipart/form-data" id="creaPost">
                                            <input type="hidden" name="mode" value="create">
                                            <input type="hidden" name="communityNome" value="<%=community.getNome()%>">
                                            <div class="form-group">
                                                <label class="form-label" for="titolo">Titolo</label>
                                                <input type="text" class="form-control mb-0" id="titolo" name="titolo"
                                                       placeholder="Inserisci titolo" required>
                                                <p class="invalid-feedback" id="error-titolo"></p>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label" for="corpo">Corpo</label>
                                                <input type="text" class="form-control mb-0" id="corpo" name="corpo"
                                                       placeholder="Inserisci corpo">
                                            </div>
                                            <div class="form-group">
                                                <label for="immagine" class="form-label custom-file-input">Inserisci
                                                    immagine</label>
                                                <input class="form-control" type="file" id="immagine" name="immagine">
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
                    <%
                        }
                    %>


                    <div class="card">
                        <div class="card-body">
                            <%
                                UtenteDAO utenteDAO = new UtenteDAO();
                                UtenteBean utenteBean = new UtenteBean();
                                for (PostBean post : community.getPost()) {
                            %>
                            <div class="post-item">
                                <div class="user-post-data py-3">
                                    <div class="d-flex align-items-center">
                                        <%
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
                                                        <a href="<%=request.getContextPath()%>/visualizzaUtente?username=<%=utenteBean.getUsername()%>"
                                                           class=""><%=utenteBean.getUsername()%>
                                                        </a>
                                                    </h5>
                                                </div>
                                            </div>
                                        </div>
                                        <%
                                            if (utente != null && (post.getUtenteEmail().equals(utente.getEmail()) || utente.isAdmin())) {
                                        %>
                                        <div class="card-post-toolbar">
                                            <button type="button" class="btn btn-link mb-1" data-bs-toggle="modal"
                                                    data-bs-target="#exampleModal">
                                                <i class="ri-delete-bin-7-line h4"></i>
                                            </button>
                                        </div>
                                        <div class="modal fade" id="exampleModal" tabindex="-1"
                                             aria-labelledby="exampleModalLabel" style="display: none;"
                                             aria-hidden="true">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="exampleModalLabel">Confermare
                                                            eliminazione?</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                                aria-label="Close">

                                                        </button>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary"
                                                                data-bs-dismiss="modal">Annulla
                                                        </button>
                                                        <a href="<%=request.getContextPath()%>/rimuoviPost?id=<%=post.getId()%>">
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
                                                    <a href="<%=request.getContextPath()%>/likePost?id=<%=post.getId()%>">
                                                        <i class="fa fa-thumbs-up" style="color: #50b5ff"></i>
                                                    </a>
                                                    <%
                                                    } else if (utente != null && !utente.get("postApprezzati").contains(post)) {
                                                    %>
                                                    <a href="<%=request.getContextPath()%>/likePost?id=<%=post.getId()%>">
                                                        <i class="fa fa-thumbs-up" style="color: #777d74"></i>
                                                    </a>
                                                    <%
                                                    } else {
                                                    %>
                                                    <i class="fa fa-thumbs-up" style="color: #777d74"></i>
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
                                                <a href="<%=request.getContextPath()%>/visualizzaPost?id=<%=post.getId()%>">
                                                    <i class="far fa-comments"></i>
                                                </a>
                                            </div>
                                            <div class="total-like-block ms-2 me-3">
                                                <a href="<%=request.getContextPath()%>/visualizzaPost?id=<%=post.getId()%>">
                                                    <label><%=post.getNumeroCommenti()%>
                                                    </label>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="card">
                        <div class="card-header d-flex justify-content-between">
                            <div class="header-title">
                                <h4 class="card-title">Informazioni</h4>
                            </div>
                        </div>
                        <div class="card-body">
                            <ul class="list-inline p-0 m-0">
                                <li class="mb-3">
                                    <div class="d-flex">
                                        <div class="flex-shrink-0">
                                            <i class="las la-user"></i>
                                        </div>
                                        <div class="flex-grow-1 ms-3">
                                            <h6>Creatore</h6>
                                            <%
                                                UtenteBean creatore = null;
                                                try {
                                                    creatore = utenteDAO.getByEmail(community.getUtenteEmail());
                                                } catch (SQLException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            %>
                                            <a href="<%=request.getContextPath()%>/visualizzaUtente?username=<%=creatore.getUsername()%>"
                                               class=" ">
                                                <p class="mb-0"><%=creatore.getUsername()%>
                                                </p>
                                            </a>
                                        </div>
                                    </div>
                                </li>
                                <li class="mb-3">
                                    <div class="d-flex">
                                        <div class="flex-shrink-0">
                                            <i class="ri-pages-line"></i>
                                        </div>
                                        <div class="flex-grow-1 ms-3">
                                            <h6>Numero di post</h6>
                                            <p class="mb-0"><%=community.getPost().size()%>
                                            </p>
                                        </div>
                                    </div>
                                </li>
                                <%
                                    if (utente != null && (community.getUtenteEmail().equals(utente.getEmail()) || utente.isAdmin())) {
                                %>
                                <li class="mb-3">
                                    <div class="d-flex">
                                        <div class="flex-shrink-0">
                                            <i class="ri-delete-bin-7-line h4"></i>
                                        </div>
                                        <div class="flex-grow-1 ms-3">


                                            <div class="card-post-toolbar">
                                                <button type="button" class="btn bg-danger" data-bs-toggle="modal"
                                                        data-bs-target="#exampleModal-community">
                                                    Elimina
                                                </button>
                                            </div>
                                            <div class="modal fade" id="exampleModal-community" tabindex="-1"
                                                 aria-labelledby="exampleModalLabel" style="display: none;"
                                                 aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="exampleModalLabel-community">
                                                                Confermare eliminazione?</h5>
                                                            <button type="button" class="btn-close"
                                                                    data-bs-dismiss="modal" aria-label="Close">

                                                            </button>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary"
                                                                    data-bs-dismiss="modal">Annulla
                                                            </button>
                                                            <a href="<%=request.getContextPath()%>/rimuoviCommunity?nome=<%=community.getNome()%>">
                                                                <button type="button" class="btn btn-primary">Conferma
                                                                </button>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </li>
                                <%
                                    }
                                %>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/assets/js/libs.min.js"></script>
<!-- slider JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/slider.js"></script>
<!-- masonry JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/masonry.pkgd.min.js"></script>
<!-- SweetAlert JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/enchanter.js"></script>
<!-- SweetAlert JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/sweetalert.js"></script>
<!-- app JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/charts/weather-chart.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/app.js"></script>
<script src="<%=request.getContextPath()%>/vendor/vanillajs-datepicker/dist/js/datepicker.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/lottie.js"></script>

<script src="<%=request.getContextPath()%>/assets/js/validazionePost.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/validazioneCommunity.js"></script>

</body>
</html>