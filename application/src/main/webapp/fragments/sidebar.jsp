<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="swagged.model.bean.IscrivitiCommunityBean" %>
<%@ page import="java.util.List" %>
<%@ page import="swagged.model.bean.CommunityBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UtenteBean utenteBean = (UtenteBean) session.getAttribute("utente");
%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SwaGGed | Homepage</title>

    <link rel="icon" href="<%=request.getContextPath()%>/assets/images/favicon.ico"/>
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
<body>
<%
    if (utenteBean != null) {
%>
<div class="iq-sidebar  sidebar-default ">
    <div id="sidebar-scrollbar">
        <br><br>
        <nav class="iq-sidebar-menu">
            <ul id="iq-sidebar-toggle" class="iq-menu">
                <li class="">
                    <a>
                        <form class="post-text ms-3 w-100 " data-bs-toggle="modal"
                              data-bs-target="#post-modal-community"
                              action="javascript:void();">
                            <button type="submit" class="btn btn-primary mb-2" data-bs-target="#post-modal">Crea Community</button>
                        </form>

                    </a>
                </li>
                <li class="">
                    <a>
                        <h5>Community Create</h5>
                    </a>
                </li>
                <%
                    if (session.getAttribute("utente") != null) {
                        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
                        List<CommunityBean> communityCreate = (List<CommunityBean>) utente.get("communityCreate");
                        for (CommunityBean community : communityCreate) {
                %>
                <li class="">
                    <a href="<%=request.getContextPath()%>/visualizzaCommunity?nome=<%= community.getNome() %>"
                       class=" ">
                        <span><%= community.getNome() %></span>
                    </a>
                </li>
                <%
                        }
                    } else {
                    }
                %>

                <li class="">
                    <a>
                        <h5>Iscrizioni</h5>
                    </a>
                </li>
                <%
                    if (session.getAttribute("utente") != null) {
                        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
                        List<IscrivitiCommunityBean> communityIscritto = (List<IscrivitiCommunityBean>) utente.get("communityIscritto");
                        for (IscrivitiCommunityBean community : communityIscritto) {
                %>
                <li class="">
                    <a href="<%=request.getContextPath()%>/visualizzaCommunity?nome=<%= community.getCommunityNome() %>"
                       class=" ">
                        <span><%= community.getCommunityNome() %></span>
                    </a>
                </li>
                <%
                        }
                    } else {
                    }
                %>
            </ul>

        </nav>

        <div class="p-5"></div>
    </div>
</div>
<%
    }
%>

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
