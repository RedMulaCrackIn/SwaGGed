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
</body>
</html>
