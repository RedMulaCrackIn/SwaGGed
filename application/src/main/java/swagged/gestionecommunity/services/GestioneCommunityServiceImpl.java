package swagged.gestionecommunity.services;

import swagged.model.bean.CommunityBean;
import swagged.model.bean.IscrivitiCommunityBean;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.CommunityDAO;
import swagged.model.dao.IscrivitiCommunityDAO;
import swagged.model.dao.PostDAO;
import swagged.model.dao.UtenteDAO;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class GestioneCommunityServiceImpl implements GestioneCommunityService {
    private CommunityDAO communityDAO = new CommunityDAO();
    private IscrivitiCommunityDAO iscrivitiCommunityDAO = new IscrivitiCommunityDAO();
    private UtenteDAO utenteDAO = new UtenteDAO();
    private PostDAO postDAO = new PostDAO();

    @Override
    public CommunityBean create(String nome, String descrizione, UtenteBean utente) throws SQLException {
        if(nome == null || nome.isEmpty() || utente == null)
            return null;

        IscrivitiCommunityBean iscrivitiCommunityBean = new IscrivitiCommunityBean();

        CommunityBean newCommunity = new CommunityBean();
        newCommunity.setNome(nome);
        newCommunity.setDescrizione(descrizione);
        newCommunity.setIscritti(0);
        newCommunity.setUtenteEmail(utente.getEmail());

        utente.add("communityCreate", newCommunity);

        if(communityDAO.save(newCommunity))
            return newCommunity;
        else
            return null;
    }

    @Override
    public boolean remove(CommunityBean community, UtenteBean utente) throws SQLException {
        if(community == null || utente == null)
            return false;

        System.out.println("Prima create: " + utente.get("communityCreate"));
        utente.remove("communityCreate", community);
        System.out.println("Dopo create: " + utente.get("communityCreate"));

        IscrivitiCommunityDAO iscrivitiCommunityDAO = new IscrivitiCommunityDAO();
        IscrivitiCommunityBean iscrivitiCommunityBean = iscrivitiCommunityDAO.getByKey(utente.getEmail(), community.getNome());

        System.out.println("Prima iscritto: " + utente.get("communityIscritto"));
        utente.remove("communityIscritto", iscrivitiCommunityBean);
        System.out.println("Dopo iscritto: " + utente.get("communityIscritto"));

        return communityDAO.delete(community.getNome());
    }


}