package id.co.myproject.nicepos_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Admin {
    @SerializedName("id_admin")
    @Expose
    private int idAdmin;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public Admin(int idAdmin, String username, String nama, String password, String avatar) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.nama = nama;
        this.password = password;
        this.avatar = avatar;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }
}
