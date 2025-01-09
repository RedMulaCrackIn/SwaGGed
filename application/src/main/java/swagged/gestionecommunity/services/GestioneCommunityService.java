package swagged.gestionecommunity.services;

import swagged.model.bean.CommunityBean;
import swagged.model.bean.UtenteBean;

import java.sql.SQLException;
import java.util.List;

public interface GestioneCommunityService {
    CommunityBean create(String nome, String descrizione, UtenteBean utente) throws SQLException;
    boolean remove(CommunityBean community, UtenteBean utente) throws SQLException;
    CommunityBean visualizza(String nome) throws SQLException;
    CommunityBean iscrizione(UtenteBean utente, String nome) throws SQLException;
    List<CommunityBean> cerca(String substring) throws SQLException;
    boolean checkNome(String nome) throws SQLException;
}