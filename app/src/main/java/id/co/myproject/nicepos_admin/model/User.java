package id.co.myproject.nicepos_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id_user")
    @Expose
    private String idUser;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("status")
    @Expose
    private String status;

    public User(String idUser, String nama, String email, String avatar, String status) {
        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.avatar = avatar;
        this.status = status;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }
}
