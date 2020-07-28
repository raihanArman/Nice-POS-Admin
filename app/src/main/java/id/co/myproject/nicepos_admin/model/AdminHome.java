package id.co.myproject.nicepos_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AdminHome {
    @SerializedName("jumlah_cafe")
    @Expose
    private String jumlahCafe;

    @SerializedName("tanggal_cafe")
    @Expose
    private Date tanggalCafe;

    @SerializedName("jumlah_supplier")
    @Expose
    private String jumlahSupplier;

    @SerializedName("tanggal_supplier")
    @Expose
    private Date tanggalSupplier;

    @SerializedName("jumlah_transaksi")
    @Expose
    private String jumlahTransaksi;

    @SerializedName("tanggal_transaksi")
    @Expose
    private Date tanggalTransaksi;

    @SerializedName("jumlah_barang")
    @Expose
    private String jumlahBarang;

    @SerializedName("tanggal_barang")
    @Expose
    private Date tanggalBarang;

    public AdminHome(String jumlahCafe, Date tanggalCafe, String jumlahSupplier, Date tanggalSupplier, String jumlahTransaksi, Date tanggalTransaksi) {
        this.jumlahCafe = jumlahCafe;
        this.tanggalCafe = tanggalCafe;
        this.jumlahSupplier = jumlahSupplier;
        this.tanggalSupplier = tanggalSupplier;
        this.jumlahTransaksi = jumlahTransaksi;
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public String getJumlahCafe() {
        return jumlahCafe;
    }

    public Date getTanggalCafe() {
        return tanggalCafe;
    }

    public String getJumlahSupplier() {
        return jumlahSupplier;
    }

    public Date getTanggalSupplier() {
        return tanggalSupplier;
    }

    public String getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public Date getTanggalBarang() {
        return tanggalBarang;
    }
}
