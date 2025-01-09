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

<div id="loading">
    <div id="loading-center">
    </div>
</div>
<div class="wrapper">
    <jsp:include page="fragments/sidebar.jsp"/>
    <jsp:include page="fragments/navbar.jsp"/>
<%
    // Controlla se il form Ã¨ stato inviato
    String type = request.getParameter("type");
    String query = request.getParameter("query");

    if (type != null && query != null && !query.trim().isEmpty()) {
        if (type.equals("post")) {
            response.sendRedirect(request.getContextPath() + "/post?mode=cerca&substring=" + URLEncoder.encode(query, "UTF-8"));
        } else if (type.equals("utente")) {
            response.sendRedirect(request.getContextPath() + "/utente?mode=cerca&substring=" + URLEncoder.encode(query, "UTF-8"));
        } else if (type.equals("community")) {
            response.sendRedirect(request.getContextPath() + "/community?mode=cerca&substring=" + URLEncoder.encode(query, "UTF-8"));
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
                                                <form action="post" method="post" enctype="multipart/form-data" id="creaPost">
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
                                                <form action="community" method="post" id="creaCommunity">
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
                                                            <a href="<%=request.getContextPath()%>/utente?mode=visualizza&username=<%=utenteBean.getUsername()%>"
                                                               class=""><%=utenteBean.getUsername()%>
                                                            </a>
                                                        </h5>
                                                        <a href="<%=request.getContextPath()%>/community?mode=visualizza&nome=<%=post.getCommunityNome()%>">
                                                            <p class="mb-0"><%=post.getCommunityNome()%>
                                                            </p>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>