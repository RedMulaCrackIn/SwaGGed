RedMula
.redmula.
Non disturbare



IN ONDA
RedMula

RedMula

Wattia
Messaggio diretto

Wattia
Cerca

regione
Automatico









Chat di
23 ottobre 2024

Wattia — 23/10/2024 22:43

[22:44]

[22:46]


Wattia — 23/10/2024 22:50

26 ottobre 2024
Wattia ha avviato una chiamata che è durata un'ora. — 26/10/2024 16:33
3 novembre 2024
RedMula ha avviato una chiamata che è durata alcuni secondi. — 03/11/2024 16:35
RedMula ha avviato una chiamata che è durata 2 ore. — 03/11/2024 16:43

Wattia — 03/11/2024 17:07
CREATE TABLE utente (
email           varchar(30)     NOT NULL,
username        varchar(20)     NOT NULL,
password        varchar(64)     NOT NULL,
immagine        varchar(512)    NOT NULL DEFAULT "",
segnalazioni    int             NOT NULL DEFAULT 0,
Mostra
message.txt
6 KB
[17:09]


RedMula — 03/11/2024 18:09

UML.vpp
728.00 KB

RedMula — 03/11/2024 18:17

Requirements Analysis Document.docx
1.42 MB
7 novembre 2024
RedMula ha avviato una chiamata che è durata un'ora. — 07/11/2024 20:34

Wattia — 07/11/2024 21:36

02. Requirement Analysis Document.pdf
4.48 MB
RedMula ha avviato una chiamata che è durata 2 ore. — 07/11/2024 21:51
11 novembre 2024
RedMula ha avviato una chiamata che è durata 3 minuti. — 11/11/2024 20:03
Wattia ha avviato una chiamata che è durata 4 ore. — 11/11/2024 20:10
15 novembre 2024

Wattia — 15/11/2024 09:56
@Interceptor
public class VisualizzazioniInterceptor {
private static final Logger logger = Logger.getLogger(VisualizzazioniInterceptor.class.getName());

@Inject
private EntityManager em;

@AroundInvoke
public Object logMethod(InvocationContext ic) throws Exception {
logger.entering(ic.getTarget().toString(), ic.getMethod().getName());

if (ic.getMethod().getName().equals("play")) {
Object[] params = ic.getParameters();
if (params != null && params.length > 0) {
long songId = (Long) params[0];  // Ottieni l'ID della canzone

// Trova la canzone nel database
Song song = em.find(Song.class, songId);
if (song != null) {
// Incrementa il numero di visualizzazioni
song.setVisualizzazioni(song.getVisualizzazioni() + 1);
em.merge(song);  // Salva le modifiche nel database
}
}
}

try {
return ic.proceed();
} finally {
logger.exiting(ic.getTarget().toString(), ic.getMethod().getName());
}
}
}
21 novembre 2024

Wattia — 21/11/2024 20:48
DROP DATABASE IF EXISTS SwaGGedDB;
CREATE DATABASE SwaGGedDB;
USE SwaGGedDB;

DROP TABLE IF EXISTS utente;
CREATE TABLE utente (
Mostra
message.txt
9 KB
8 dicembre 2024
RedMula ha avviato una chiamata che è durata 3 ore. — 08/12/2024 16:07

Wattia — 08/12/2024 16:08


RedMula — 08/12/2024 16:15

Requirements Analysis Document.docx
3.55 MB

RedMula — 08/12/2024 17:08

System Design Document.docx
332.07 KB

Wattia — 08/12/2024 18:58

Requirements Analysis Document (1).docx
3.72 MB

System Design Document.docx
333.01 KB
13 dicembre 2024
Hai una chiamata senza risposta da Wattia della durata di alcuni secondi. — 13/12/2024 17:20
RedMula ha avviato una chiamata che è durata 2 ore. — 13/12/2024 17:20

Wattia — 13/12/2024 18:35

Requirements Analysis Document.docx
4.02 MB

RedMula — 13/12/2024 18:39

Object_Design_Document_SwaGGed.docx
3.25 MB
[18:41]

System Design Document.docx
333.58 KB
19 dicembre 2024
RedMula ha avviato una chiamata che è durata 3 ore. — 19/12/2024 20:38

Wattia — 19/12/2024 20:41
registrazione
accesso
creazione post
creazione commento
creazione community

Wattia — 19/12/2024 22:55

Crea Post.docx
23.03 KB
2 gennaio 2025
RedMula ha avviato una chiamata che è durata un'ora. — 02/01/2025 15:20
RedMula ha avviato una chiamata che è durata 3 ore. — 02/01/2025 16:49
8 gennaio 2025
RedMula ha avviato una chiamata che è durata 2 ore. — Ieri alle 14:21
RedMula ha avviato una chiamata che è durata 3 ore. — Ieri alle 17:02

Wattia — Ieri alle 17:40
DROP DATABASE IF EXISTS SwaGGedDB;
CREATE DATABASE SwaGGedDB;
USE SwaGGedDB;

DROP TABLE IF EXISTS utente;
CREATE TABLE utente (
Mostra
db.txt
10 KB
package swagged.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
Mostra
DriverManagerConnectionPool.txt
2 KB
[17:40]
SwaGGedDB

Wattia — Ieri alle 18:44
package swagged.model.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
Mostra
PostBean.txt
4 KB
[18:49]
package swagged.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class ApprezzaPostBean implements Serializable, Bean {
Mostra
ApprezzaPostBean.txt
2 KB

Wattia — Ieri alle 19:00
package swagged.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
Mostra
UtenteBean.txt
7 KB
9 gennaio 2025
RedMula ha avviato una chiamata che è durata 2 ore. — Oggi alle 10:40

Wattia — Oggi alle 10:41
package swagged.model.dao;

import swagged.model.bean.CommentoBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
Mostra
CommentoDAO.txt
8 KB

Wattia — Oggi alle 10:50
package swagged.model.dao;

import swagged.model.bean.IscrivitiCommunityBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
Mostra
IscrivitiCommunityDAO.txt
5 KB

Wattia — Oggi alle 11:08
package swagged.gestionecommunity.services;

import swagged.model.bean.CommunityBean;
import swagged.model.bean.UtenteBean;

import java.sql.SQLException;
Mostra
message.txt
1 KB
[11:09]
package swagged.gestionecommunity.services;

import swagged.model.bean.CommunityBean;
import swagged.model.bean.IscrivitiCommunityBean;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;
Mostra
message.txt
6 KB

Wattia — Oggi alle 11:22
package swagged.gestioneutenti.services;

import swagged.model.bean.UtenteBean;

import javax.servlet.GenericServlet;
import javax.servlet.http.Part;
Mostra
message.txt
1 KB
[11:27]
package swagged.gestioneutenti.services;

import swagged.model.bean.CommunityBean;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.*;
Mostra
message.txt
8 KB

Wattia — Oggi alle 11:45
public class CommunityServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
private static final GestioneCommunityServiceImpl gestioneCommunity = new GestioneCommunityServiceImpl();

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String mode = request.getParameter("mode");
Mostra
message.txt
4 KB
NOVITÀ

Wattia — Oggi alle 12:30
public class UtenteServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
private static final GestioneUtentiServiceImpl gestioneUtenti = new GestioneUtentiServiceImpl();

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String mode = request.getParameter("mode");
Mostra
message.txt
5 KB
NOVITÀ
Wattia ha avviato una chiamata. — Oggi alle 16:10

Wattia — Oggi alle 16:14



NOVITÀ
[16:16]
Nero -> #3f414d
NOVITÀ
NOVITÀ

Wattia — Oggi alle 16:47
<%@ page import="swagged.model.bean.UtenteBean" %>
<%@ page import="swagged.model.bean.CommunityBean" %>
<%@ page import="swagged.model.bean.IscrivitiCommunityBean" %>
<%@ page import="swagged.model.bean.PostBean" %>
<%@ page import="swagged.model.dao.PostDAO" %>
<%@ page import="swagged.model.dao.CommunityDAO" %>
Mostra
homepage.txt
23 KB
NOVITÀ

Invia un messaggio in @Wattia
﻿




per selezionare

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
                                                        <i class="fa fa-thumbs-up" style="color: #50b5ff"></i>
                                                    </a>
                                                    <%
                                                    } else if(utente != null && !utente.get("postApprezzati").contains(post)){
                                                    %>
                                                    <a href="<%=request.getContextPath()%>/post?mode=like&id=<%=post.getId()%>">
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
                                                <a href="<%=request.getContextPath()%>/post?mode=visualizza&id=<%=post.getId()%>">
                                                    <i class="far fa-comments"></i>
                                                </a>
                                            </div>
                                            <div class="total-like-block ms-2 me-3">
                                                <a href="<%=request.getContextPath()%>/post?mode=visualizza&id=<%=post.getId()%>">
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
homepage.txt
23 KB