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
