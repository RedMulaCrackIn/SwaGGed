package swagged.gestionecommenti.services;

import swagged.model.bean.CommentoBean;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.CommentoDAO;
import swagged.model.dao.PostDAO;
import swagged.model.dao.UtenteDAO;

import java.sql.SQLException;
import java.util.List;

public class GestioneCommentiServiceImpl implements GestioneCommentiService{
    private CommentoDAO commentoDAO;
    private UtenteDAO utenteDAO;
    private PostDAO postDAO;

    // Costruttore vuoto che inizializza i DAO con new
    public GestioneCommentiServiceImpl() {
        this.commentoDAO = new CommentoDAO();
        this.utenteDAO = new UtenteDAO();
        this.postDAO = new PostDAO();
    }

    // Costruttore che accetta i DAO come parametri
    public GestioneCommentiServiceImpl(CommentoDAO commentoDAO, UtenteDAO utenteDAO, PostDAO postDAO) {
        this.commentoDAO = commentoDAO;
        this.utenteDAO = utenteDAO;
        this.postDAO = postDAO;
    }

    @Override
    public CommentoBean create(int postId, String corpo, String utenteEmail) throws SQLException {
        if(corpo == null || corpo.isEmpty() || utenteEmail == null || utenteEmail.isEmpty() || utenteDAO.getByEmail(utenteEmail) == null || postDAO.getById(postId) == null)
            return null;

        CommentoBean newCommento = new CommentoBean();
        newCommento.setCorpo(corpo);
        newCommento.setUtenteEmail(utenteEmail);
        newCommento.setPostId(postId);

        PostDAO postDAO = new PostDAO();
        PostBean postBean = postDAO.getById(postId);
        postBean.aumentaNumeroCommenti();
        postBean.addCommento(newCommento);
        postDAO.update(postBean);

        UtenteBean utente = utenteDAO.getByEmail(utenteEmail);
        utente.add("commentiCreati", newCommento);
        postBean.addCommento(newCommento);

        if(commentoDAO.save(newCommento))
            return newCommento;
        else
            return null;
    }


    @Override
    public boolean remove(int id, int postId, UtenteBean utente) throws SQLException {
        if(commentoDAO.getById(id) == null)
            return false;

        PostBean postBean = postDAO.getById(postId);
        postBean.diminuisciNumeroCommenti();
        postDAO.update(postBean);

        CommentoBean commento = commentoDAO.getById(id);

        PostBean post = postDAO.getById(id);
        post.removeCommento(commento);

        utente.remove("commentiCreati", commento);

        return commentoDAO.delete(id);
    }


    @Override
    public List<CommentoBean> visualizza(int postId) throws SQLException {
        if(postDAO.getById(postId) == null)
            return null;

        return commentoDAO.getByPostId(postId);
    }
}
