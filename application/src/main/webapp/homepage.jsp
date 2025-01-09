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