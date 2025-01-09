<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
%>
<html>
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
    <style>
        button.btn-link {
            background: none; /* Rimuove lo sfondo */
            border: none; /* Rimuove il bordo */
            padding: 0; /* Rimuove il padding, se presente */
            cursor: pointer; /* Aggiunge il cursore tipo mano quando si passa sopra */
        }
    </style>
</head>
<body>

<div class="iq-top-navbar">
    <div class="iq-navbar-custom">
        <nav class="navbar navbar-expand-lg navbar-light p-0">
            <div class="iq-navbar-logo d-flex justify-content-between">
                <a href="<%=request.getContextPath()%>/homepage.jsp">
                    <img src="<%=request.getContextPath()%>/assets/images/logo.png" class="img-fluid" alt="">
                    <span>SwaGGed</span>
                </a>
                <%
                    if (utente != null) {
                %>
                <div class="iq-menu-bt align-self-center">
                    <div class="wrapper-menu">
                        <div class="main-circle"><i class="ri-menu-line"></i></div>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
            <div class="iq-search-bar device-search">
                <form action="<%=request.getContextPath() + "/ricerca.jsp"%>" class="searchbox" method="get">
                    <button class="btn-link search-link" href="" type="submit"><i class="ri-search-line"></i></button>
                    <input type="text" class="text search-input" placeholder="Cerca contenuti" name="query">
                    <select class="form-select form-select-sm mb-3" name="type">
                        <option selected="" value="post">Post</option>
                        <option value="utente">Utente</option>
                        <option value="community">Community</option>
                    </select>
                </form>
            </div>
            <%
                if (utente != null) {
            %>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav  ms-auto navbar-list">
                    <li class="nav-item dropdown">
                        <a href="#" class="   d-flex align-items-center dropdown-toggle" id="drop-down-arrow"
                           data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="<%=request.getContextPath() + "/assets/images/pfp/" + utente.getImmagine()%>"
                                 class="img-fluid rounded-circle me-3" alt="user">
                            <div class="caption">
                                <h6 class="mb-0 line-height"><%=utente.getUsername()%>
                                </h6>
                            </div>
                        </a>
                        <div class="sub-drop dropdown-menu caption-menu" aria-labelledby="drop-down-arrow">
                            <div class="card shadow-none m-0">
                                <div class="card-header  bg-primary">
                                    <div class="header-title">
                                        <h5 class="mb-0 text-white">Ciao <%=utente.getUsername()%>
                                        </h5>
                                    </div>
                                </div>
                                <div class="card-body p-0 ">
                                    <a href="<%=request.getContextPath()%>/visualizzaUtente?username=<%=utente.getUsername()%>"
                                       class="iq-sub-card iq-bg-primary-hover">
                                        <div class="d-flex align-items-center">
                                            <div class="rounded card-icon bg-soft-primary">
                                                <i class="ri-file-user-line"></i>
                                            </div>
                                            <div class="ms-3">
                                                <h6 class="mb-0 ">Profilo</h6>
                                            </div>
                                        </div>
                                    </a>
                                    <div class="d-inline-block w-100 text-center p-3">
                                        <a class="btn btn-primary iq-sign-btn"
                                           href="<%=request.getContextPath()%>/logout" role="button">Logout<i
                                                class="ri-login-box-line ms-2"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <%
            } else {
            %>
            <div class="   d-flex align-items-center">
                <a class="btn btn-primary iq-sign-btn" href="<%=request.getContextPath()%>/login.jsp" role="button">Accedi<i
                        class="ri-login-box-line ms-2"></i></a>
            </div>
            <%
                }
            %>
        </nav>
    </div>
</div>

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
</body>
</html>
