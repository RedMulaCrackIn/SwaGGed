package swagged.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class IscrivitiCommunityBean implements Serializable, Bean {
    private static final long serialVersionUID = 1L;
    private String utenteEmail;
    private String communityNome;

    public IscrivitiCommunityBean() {
        this.utenteEmail = "";
        this.communityNome = "";
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

    @Override
    public String toString() {
        return "IscrivitiCommunityBean{" +
                "utenteEmail='" + utenteEmail + '\'' +
                ", communityNome='" + communityNome + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        IscrivitiCommunityBean that = (IscrivitiCommunityBean) obj;
        return Objects.equals(utenteEmail, that.utenteEmail) &&
                Objects.equals(communityNome, that.communityNome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utenteEmail, communityNome);
    }

}
