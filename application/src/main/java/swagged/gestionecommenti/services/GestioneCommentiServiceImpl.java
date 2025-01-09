package swagged.gestionecommenti.services;

import swagged.model.bean.CommentoBean;
import swagged.model.bean.UtenteBean;

import java.sql.SQLException;
import java.util.List;

public class GestioneCommentiServiceImpl implements GestioneCommentiService{
    @Override
    public CommentoBean create(int postId, String corpo, String utenteEmail) throws SQLException {
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
