package id.co.myproject.nicepos_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supplier {
    @SerializedName("id_supplier")
    @Expose
    private String idSupplier;

    @SerializedName("nama_supplier")
    @Expose
    private String namaSupplier;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("alamat")
    @Expose
    private String alamat;


    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("no_telp")
    @Expose
    private String no_telp;

    @SerializedName("status")
    @Expose
    private String status;

    public Supplier(String idSupplier, String namaSupplier, String avatar, String alamat, String email, String no_telp, String status) {
        this.idSupplier = idSupplier;
        this.namaSupplier = namaSupplier;
        this.avatar = avatar;
        this.alamat = alamat;
        this.email = email;
        this.no_telp = no_telp;
        this.status = status;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getEmail() {
        return email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getStatus() {
        return status;
    }
}
