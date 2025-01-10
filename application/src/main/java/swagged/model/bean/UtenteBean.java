package swagged.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UtenteBean implements Serializable, Bean {
    private static final long serialVersionUID = 1L;
    private String email;
    private String username;
    private String password;
    private String immagine;
    private boolean bandito;
    private boolean admin;

    private List<PostBean> postCreati;
    private List<ApprezzaPostBean> postApprezzati;
    private List<CommentoBean> commentiCreati;
    private List<CommunityBean> communityCreate;
    private List<IscrivitiCommunityBean> communityIscritto;

    

    public UtenteBean() {
        this.email = "";
        this.username = "";
        this.password = "";
        this.immagine = "";
        this.bandito = false;
        this.admin = false;
        postCreati = new ArrayList<PostBean>();
        postApprezzati = new ArrayList<>();
        commentiCreati = new ArrayList<>();
        communityCreate = new ArrayList<>();
        communityIscritto = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public boolean isBandito() {
        return bandito;
    }

    public void setBandito(boolean bandito) {
        this.bandito = bandito;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<?> get(String tipo){
        switch (tipo) {
            case "postCreati":
                return postCreati;
            case "postApprezzati":
                return postApprezzati;
            case "commentiCreati":
                return commentiCreati;
            case "communityCreate":
                return communityCreate;
            case "communityIscritto":
                return communityIscritto;
            default:
                return null;
        }
    }

    public boolean set(String tipo, List<? extends Bean> beans) {
        if (beans == null || beans.isEmpty()) {
            System.out.println("Setting communityIscritto with " + beans.size() + " items.");
            return false;
        }

        switch (tipo) {
            case "postCreati":
                if (beans.get(0) instanceof PostBean) {
                    postCreati.clear(); // Svuota la lista esistente
                    postCreati.addAll((List<PostBean>) beans); // Aggiunge tutti i nuovi elementi
                    return true;
                }
                break;
            case "postApprezzati":
                if (beans.get(0) instanceof ApprezzaPostBean) {
                    postApprezzati.clear();
                    postApprezzati.addAll((List<ApprezzaPostBean>) beans);
                    return true;
                }
                break;
            case "commentiCreati":
                if (beans.get(0) instanceof CommentoBean) {
                    commentiCreati.clear();
                    commentiCreati.addAll((List<CommentoBean>) beans);
                    return true;
                }
                break;
            case "communityCreate":
                if (beans.get(0) instanceof CommunityBean) {
                    communityCreate.clear();
                    communityCreate.addAll((List<CommunityBean>) beans);
                    return true;
                }
                break;
            case "communityIscritto":
                if (beans.get(0) instanceof IscrivitiCommunityBean) {
                    communityIscritto.clear();
                    communityIscritto.addAll((List<IscrivitiCommunityBean>) beans);
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }


    public boolean add(String tipo, Bean bean) {
        switch (tipo) {
            case "postCreati":
                if (bean instanceof PostBean) {
                    return postCreati.add((PostBean) bean);
                }
                break;
            case "postApprezzati":
                if (bean instanceof ApprezzaPostBean) {
                    return postApprezzati.add((ApprezzaPostBean) bean);
                }
                break;
            case "commentiCreati":
                if (bean instanceof CommentoBean) {
                    return commentiCreati.add((CommentoBean) bean);
                }
                break;
            case "communityCreate":
                if (bean instanceof CommunityBean) {
                    return communityCreate.add((CommunityBean) bean);
                }
                break;
            case "communityIscritto":
                if (bean instanceof IscrivitiCommunityBean) {
                    return communityIscritto.add((IscrivitiCommunityBean) bean);
                }
                break;
            default:
                return false;
        }
        return false;
    }

    public boolean remove(String tipo, Bean bean) {
        switch (tipo) {
            case "postCreati":
                if (bean instanceof PostBean) {
                    return postCreati.remove(bean);
                }
                break;
            case "postApprezzati":
                if (bean instanceof ApprezzaPostBean) {
                    return postApprezzati.remove(bean);
                }
                break;
            case "commentiCreati":
                if (bean instanceof CommentoBean) {
                    return commentiCreati.remove(bean);
                }
                break;
            case "communityCreate":
                if (bean instanceof CommunityBean) {
                    return communityCreate.remove(bean);
                }
                break;
            case "communityIscritto":
                if (bean instanceof IscrivitiCommunityBean) {
                    return communityIscritto.remove(bean);
                }
                break;
            default:
                return false;
        }
        return false;
    }

}