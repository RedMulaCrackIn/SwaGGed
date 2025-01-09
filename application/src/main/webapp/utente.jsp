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

