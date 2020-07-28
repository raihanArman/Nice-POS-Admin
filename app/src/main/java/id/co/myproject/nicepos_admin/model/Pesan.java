package id.co.myproject.nicepos_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Pesan {
    @SerializedName("id_pesan")
    @Expose
    private String idPesan;

    @SerializedName("id_user")
    @Expose
    private String idUser;

    @SerializedName("nama_user")
    @Expose
    private String namaUser;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("level")
    @Expose
    private String level;

    @SerializedName("isi")
    @Expose
    private String isi;

    @SerializedName("tanggal")
    @Expose
    private Date tanggal;

    public Pesan(String idPesan, String idUser, String namaUser, String email, String level, String isi, Date tanggal) {
        this.idPesan = idPesan;
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.email = email;
        this.level = level;
        this.isi = isi;
        this.tanggal = tanggal;
    }

    public String getIdPesan() {
        return idPesan;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getEmail() {
        return email;
    }

    public String getLevel() {
        return level;
    }

    public String getIsi() {
        return isi;
    }

    public Date getTanggal() {
        return tanggal;
    }
}
