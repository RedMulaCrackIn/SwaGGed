package swagged.model.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostBean implements Serializable, Bean {
    private static final long serialVersionUID = 1L;
    private int id;
    private String titolo;
    private String corpo;
    private String immagine;
    private int likes;
    private Date dataCreazione;
    private int numeroCommenti;
    private String utenteEmail;
    private String communityNome;

    private List<CommentoBean> commenti;

    public PostBean() {
        this.id = -1;
        this.titolo = "";
        this.corpo = "";
        this.immagine = "";
        this.likes = 0;
        this.dataCreazione = new Date(-1);
        this.numeroCommenti = 0;
        this.utenteEmail = "";
        this.communityNome = "";
        commenti = new ArrayList<CommentoBean>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void aggiungiLike() {
        this.likes++;
    }

    public void rimuoviLike() {
        this.likes--;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public int getNumeroCommenti() {
        return numeroCommenti;
    }

    public void setNumeroCommenti(int numeroCommenti) {
        this.numeroCommenti = numeroCommenti;
    }

    public void aumentaNumeroCommenti() {
        this.numeroCommenti++;
    }

    public void diminuisciNumeroCommenti() {
        this.numeroCommenti--;
    }

    public String getUtenteEmail() {
        return utenteEmail;
    }

    public void setUtenteEmail(String utenteEmail) {
        this.utenteEmail = utenteEmail;
    }

    public String getCommunityNome() {
        return communityNome;
    }

    public void setCommunityNome(String communityNome) {
        this.communityNome = communityNome;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<CommentoBean> getCommenti() {
        return commenti;
    }

    public void setCommenti(List<CommentoBean> commenti) {
        this.commenti = commenti;
    }

    public boolean addCommento(CommentoBean commento){
        return this.commenti.add(commento);
    }

    public boolean removeCommento(CommentoBean commento){
        return this.commenti.remove(commento);
    }

    public int compareTo(PostBean other) {
        // Ordina dalla data più recente alla più vecchia
        return other.getDataCreazione().compareTo(this.getDataCreazione());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PostBean other = (PostBean) obj;
        return this.id == other.id; // Confronta in base all'ID univoco del post
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Genera un hash basato sull'ID
    }

}
