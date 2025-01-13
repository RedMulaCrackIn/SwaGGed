<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="swagged.model.bean.CommunityBean" %>
<%@ page import="swagged.model.bean.IscrivitiCommunityBean" %>
<%@ page import="swagged.model.bean.PostBean" %>
<%@ page import="swagged.model.dao.PostDAO" %>
<%@ page import="swagged.model.dao.CommunityDAO" %>
<%@ page import="swagged.model.dao.UtenteDAO" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    PostDAO postDAO = new PostDAO();
    CommunityDAO communityDAO = new CommunityDAO();
    List<PostBean> posts = new ArrayList<>();
    if (utente != null) {
        if (!utente.get("communityIscritto").isEmpty()) {
            List<IscrivitiCommunityBean> communities = (List<IscrivitiCommunityBean>) utente.get("communityIscritto");
            for (IscrivitiCommunityBean community : communities) {
                CommunityBean communityBean = communityDAO.getByNome(community.getCommunityNome());
                posts.addAll(postDAO.getByCommunityNome(communityBean.getNome()));
            }
            posts.sort(Comparator.comparing(PostBean::getDataCreazione).reversed());
        } else {
            posts = postDAO.getByDate();
        }
    } else{
        posts = postDAO.getByDate();
    }
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
<div id="loading">
    <div id="loading-center">
    </div>
</div>
<div class="wrapper">
    <jsp:include page="fragments/sidebar.jsp"/>
    <jsp:include page="fragments/navbar.jsp"/>
    <%
        // Controlla se il form è stato inviato
        String type = request.getParameter("type");
        String query = request.getParameter("query");

        if (type != null && query != null && !query.trim().isEmpty()) {
            if (type.equals("post")) {
                response.sendRedirect(request.getContextPath() + "/cercaPost?substring=" + URLEncoder.encode(query, "UTF-8"));
            } else if (type.equals("utente")) {
                response.sendRedirect(request.getContextPath() + "/cercaUtente?substring=" + URLEncoder.encode(query, "UTF-8"));
            } else if (type.equals("community")) {
                response.sendRedirect(request.getContextPath() + "/cercaCommunity?substring=" + URLEncoder.encode(query, "UTF-8"));
            }
        }
    %>

    <div id="content-page" class="content-page">
        <div class="container">
            <div class="row row-eq-height">
                <div class="col-md-12">
                    <div class="col-md-12">


                        <%
                            if(utente != null){
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
                                    <form class="post-text ms-3 w-100 " data-bs-toggle="modal"
                                          data-bs-target="#post-modal"
                                          action="javascript:void();">
                                        <input type="text" class="form-control rounded"
                                               placeholder="Scrivi qualcosa" style="border:none;">
                                    </form>
                                </div>
                                <hr>
                            </div>
                            <div class="modal fade" id="post-modal" tabindex="-1" aria-labelledby="post-modalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-dialog-scrollable">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="post-modalLabel">Crea Post</h5>
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><i
                                                    class="ri-close-fill"></i></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="creaPost" method="post" enctype="multipart/form-data" id="creaPost">
                                                <input type="hidden" name="mode" value="create">
                                                <div class="form-group">
                                                    <label class="form-label" for="communityNome">Nome community</label>
                                                    <input type="text" class="form-control mb-0" id="communityNome"
                                                           name="communityNome"
                                                           placeholder="Inserisci il nome della community in cui pubblicare il post"
                                                           required>
                                                    <p class="invalid-feedback" id="error-communityPost"></p>
                                                </div>
                                                <div class="form-group">
                                                    <label class="form-label" for="titolo">Titolo</label>
                                                    <input type="text" class="form-control mb-0" id="titolo"
                                                           name="titolo"
                                                           placeholder="Inserisci titolo" required>
                                                    <p class="invalid-feedback" id="error-titolo"></p>
                                                </div>
                                                <div class="form-group">
                                                    <label class="form-label" for="corpo">Corpo</label>
                                                    <input type="text" class="form-control mb-0" id="corpo" name="corpo"
                                                           placeholder="Inserisci corpo">
                                                </div>
                                                <div class="form-group">
                                                    <label for="immagine"
                                                           class="form-label custom-file-input">Immagine</label>
                                                    <input class="form-control" type="file" id="immagine"
                                                           name="immagine">
                                                </div>
                                                <hr>
                                                <button type="submit" class="btn btn-primary d-block w-100 mt-3">Crea
                                                </button>
                                            </form>
                                        </div>
                                    </div>
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
                                            <form action="creaCommunity" method="post" id="creaCommunity">
                                                <input type="hidden" name="mode" value="create">
                                                <div class="form-group">
                                                    <label class="form-label" for="communityNomeCreazione">Nome community</label>
                                                    <input type="text" class="form-control mb-0" id="communityNomeCreazione"
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
                                                <button type="submit" class="btn btn-primary d-block w-100 mt-3">Crea</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>


                    <%
                        for (PostBean post : posts) {
                            utenteBean = utenteDAO.getByEmail(post.getUtenteEmail());
                    %>
                    <div class="col-sm-12">
                        <div class="card card-block card-stretch card-height">
                            <div class="card-body">
                                <div class="user-post-data py-3">
                                    <div class="d-flex justify-content-between">
                                        <div class="me-3">
                                            <img class="avatar-60 rounded-circle"
                                                 src="<%=request.getContextPath() + "/assets/images/pfp/" + utenteBean.getImmagine()%>"
                                                 alt="">
                                        </div>
                                        <div class="w-100">
                                            <div class="d-flex justify-content-between">
                                                <div class="">
                                                    <h5 class="mb-0 d-inline-block">
                                                        <a href="<%=request.getContextPath()%>/visualizzaUtente?username=<%=utenteBean.getUsername()%>"
                                                           class=""><%=utenteBean.getUsername()%>
                                                        </a>
                                                    </h5>
                                                    <a href="<%=request.getContextPath()%>/visualizzaCommunity?nome=<%=post.getCommunityNome()%>">
                                                        <p class="mb-0"><%=post.getCommunityNome()%>
                                                        </p>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <%
                                            if(utente != null &&(post.getUtenteEmail().equals(utente.getEmail()) || utente.isAdmin())){
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
                                                        <a href="<%=request.getContextPath()%>/rimuoviPost?id=<%=post.getId()%>">
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
                                                    <a href="<%=request.getContextPath()%>/likePost?id=<%=post.getId()%>">
                                                        <i class="fa fa-thumbs-up" style="color: #50b5ff"></i>
                                                    </a>
                                                    <%
                                                    } else if(utente != null && !utente.get("postApprezzati").contains(post)){
                                                    %>
                                                    <a href="<%=request.getContextPath()%>/likePost?id=<%=post.getId()%>">
                                                        <i class="fa fa-thumbs-up" style="color: #777d74"></i>
                                                    </a>
                                                    <%
                                                    } else{
                                                    %>
                                                    <i class="fa fa-thumbs-up" style="color: #777d74"></i>
                                                    <%
                                                        }
                                                    %>
                                                </div>
                                                <div class="total-like-block ms-2 me-3">
                                                    <label><%=post.getLikes()%></label>
                                                </div>
                                            </div>
                                            <div class="like-data">
                                                <a href="<%=request.getContextPath()%>/visualizzaPost?id=<%=post.getId()%>">
                                                    <i class="far fa-comments"></i>
                                                </a>
                                            </div>
                                            <div class="total-like-block ms-2 me-3">
                                                <a href="<%=request.getContextPath()%>/visualizzaPost?id=<%=post.getId()%>">
                                                    <label><%=post.getNumeroCommenti()%></label>
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
</div>
<!-- Wrapper End-->
<!-- Backend Bundle JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/libs.min.js"></script>
<!-- Slider JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/slider.js"></script>
<!-- Masonry JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/masonry.pkgd.min.js"></script>
<!-- SweetAlert JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/enchanter.js"></script>
<!-- SweetAlert JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/sweetalert.js"></script>
<!-- Weather Chart JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/charts/weather-chart.js"></script>
<!-- App JavaScript -->
<script src="<%=request.getContextPath()%>/assets/js/app.js"></script>
<script src="<%=request.getContextPath()%>/vendor/vanillajs-datepicker/dist/js/datepicker.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/lottie.js"></script>

<script src="<%=request.getContextPath()%>/assets/js/validazionePost.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/validazioneCommunity.js"></script>



</body>
</html>