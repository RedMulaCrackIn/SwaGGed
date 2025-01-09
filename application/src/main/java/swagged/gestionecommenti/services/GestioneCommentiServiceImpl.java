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
    private CommentoDAO commentoDAO = new CommentoDAO();
    private UtenteDAO utenteDAO = new UtenteDAO();
    private PostDAO postDAO = new PostDAO();

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
        return false;
    }

    @Override
    public List<CommentoBean> visualizza(int postId) throws SQLException {
        return List.of();
    }
}
