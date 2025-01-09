package swagged.gestionecommenti.services;

import swagged.model.bean.CommentoBean;
import swagged.model.bean.UtenteBean;

import java.sql.SQLException;
import java.util.List;

public interface GestioneCommentiService {
    CommentoBean create(int postId, String corpo, String utenteEmail) throws SQLException;
    boolean remove(int id, int postId, UtenteBean utente) throws SQLException;
    List<CommentoBean> visualizza(int postId) throws SQLException;
}
