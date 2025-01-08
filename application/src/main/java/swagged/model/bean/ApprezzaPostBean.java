package swagged.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class ApprezzaPostBean implements Serializable, Bean {
    private static final long serialVersionUID = 1L;
    private String utenteEmail;
    private int postId;

    public ApprezzaPostBean() {
        this.utenteEmail = "";
        this.postId = -1;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ApprezzaPostBean{" +
                "utenteEmail='" + utenteEmail + '\'' +
                ", postId=" + postId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ApprezzaPostBean that = (ApprezzaPostBean) obj;
        return Objects.equals(utenteEmail, that.utenteEmail) &&
                Objects.equals(postId, that.postId);
    }
}