package swagged.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class CommentoBean implements Serializable, Bean {
    private static final long serialVersionUID = 1L;
    private int id;
    private String corpo;
    private String utenteEmail;
    private int postId;

    public CommentoBean() {
        this.id = -1;
        this.corpo = "";
        this.utenteEmail = "";
        this.postId = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getUtenteEmail() {
        return utenteEmail;
    }

    public void setUtenteEmail(String utenteEmail) {
        this.utenteEmail = utenteEmail;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CommentoBean that = (CommentoBean) obj;
        return Objects.equals(id, that.id);
    }
}
