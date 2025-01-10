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

public class GestioneCommunityServiceImpl implements GestioneCommunityService
{
    private CommunityDAO communityDAO;
    private IscrivitiCommunityDAO iscrivitiCommunityDAO;
    private UtenteDAO utenteDAO;
    private PostDAO postDAO;

    // Costruttore vuoto (crea nuove istanze dei DAO)
    public GestioneCommunityServiceImpl() {
        this.communityDAO = new CommunityDAO();
        this.iscrivitiCommunityDAO = new IscrivitiCommunityDAO();
        this.utenteDAO = new UtenteDAO();
        this.postDAO = new PostDAO();
    }

    // Costruttore che riceve i DAO come parametri
    public GestioneCommunityServiceImpl(CommunityDAO communityDAO, IscrivitiCommunityDAO iscrivitiCommunityDAO, UtenteDAO utenteDAO, PostDAO postDAO) {
        this.communityDAO = communityDAO;
        this.iscrivitiCommunityDAO = iscrivitiCommunityDAO;
        this.utenteDAO = utenteDAO;
        this.postDAO = postDAO;
    }

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

        utente.remove("communityCreate", community);

        IscrivitiCommunityDAO iscrivitiCommunityDAO = new IscrivitiCommunityDAO();
        IscrivitiCommunityBean iscrivitiCommunityBean = iscrivitiCommunityDAO.getByKey(utente.getEmail(), community.getNome());

        utente.remove("communityIscritto", iscrivitiCommunityBean);

        return communityDAO.delete(community.getNome());
    }

    @Override
    public CommunityBean visualizza(String nome) throws SQLException {
        if(nome == null || nome.isEmpty() || communityDAO.getByNome(nome) == null)
            return null;
        List<PostBean> post = postDAO.getByCommunityNome(nome);
        CommunityBean communityBean = communityDAO.getByNome(nome);

        post.sort(Comparator.comparing(PostBean::getDataCreazione));

        communityBean.setPost(post);
        return communityBean;
    }

    @Override
    public CommunityBean iscrizione(UtenteBean utente, String nome) throws SQLException {
        if(utente == null || nome == null || nome.isEmpty() || communityDAO.getByNome(nome) == null)
            return null;

        IscrivitiCommunityBean iscrivitiCommunityBean = new IscrivitiCommunityBean();

        if((iscrivitiCommunityDAO.getByKey(utente.getEmail(), nome)) == null) {
            iscrivitiCommunityBean.setUtenteEmail(utente.getEmail());
            iscrivitiCommunityBean.setCommunityNome(nome);
            iscrivitiCommunityDAO.save(iscrivitiCommunityBean);

            CommunityBean communityBean = communityDAO.getByNome(nome);
            communityBean.aggiungiIscritto();

            //UtenteBean utente = utenteDAO.getByEmail(utenteEmail);
            utente.add("communityIscritto", iscrivitiCommunityBean);

            if(communityDAO.update(communityBean))
                return communityBean;
            else
                return null;

        } else {
            iscrivitiCommunityBean = iscrivitiCommunityDAO.getByKey(utente.getEmail(), nome);
            iscrivitiCommunityDAO.delete(utente.getEmail(), nome);

            CommunityBean communityBean = communityDAO.getByNome(nome);
            communityBean.rimuoviIscritto();

            //UtenteBean utente = utenteDAO.getByEmail(utenteEmail);
            utente.remove("communityIscritto", iscrivitiCommunityBean);

            if(communityDAO.update(communityBean))
                return communityBean;
            else
                return null;
        }
    }

    @Override
    public List<CommunityBean> cerca(String substring) throws SQLException {
        if(substring == null || substring.isEmpty())
            return null;
        return communityDAO.getByNameSubstring(substring);
    }

    @Override
    public boolean checkNome(String nome) throws SQLException {
        if(nome == null || nome.isEmpty())
            return false;
        CommunityBean communityBean = communityDAO.getByNome(nome);
        return communityBean != null;
    }
}