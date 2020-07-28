package id.co.myproject.nicepos_admin.request;

import java.util.List;

import id.co.myproject.nicepos_admin.model.Admin;
import id.co.myproject.nicepos_admin.model.AdminHome;
import id.co.myproject.nicepos_admin.model.BarangSupplier;
import id.co.myproject.nicepos_admin.model.Cafe;
import id.co.myproject.nicepos_admin.model.Pesan;
import id.co.myproject.nicepos_admin.model.Pesanan;
import id.co.myproject.nicepos_admin.model.Request;
import id.co.myproject.nicepos_admin.model.Supplier;
import id.co.myproject.nicepos_admin.model.User;
import id.co.myproject.nicepos_admin.model.Value;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("tampil_home_admin.php")
    Call<AdminHome> getHomeAdminRequest();


    @GET("tampil_cafe.php")
    Call<List<Cafe>> getCafeListRequest();

    @GET("supplier/tampil_supplier.php")
    Call<List<Supplier>> getSupplierListRequest();


    @GET("supplier/tampil_notif_supplier.php")
    Call<List<Request>> getTransaksiListRequest();

    @GET("supplier/tampil_pesanan.php")
    Call<List<Pesanan>> getPesananSupplierRequest(
            @Query("id_request") String idRequest
    );


    @GET("supplier/tampil_barang.php")
    Call<List<BarangSupplier>> getBarangRequest();

    @GET("supplier/tampil_barang.php")
    Call<BarangSupplier> getBarangItemRequest(
            @Query("id_barang") String idBarang
    );

    @GET("supplier/tampil_supplier.php")
    Call<Supplier> getSupplierItemRequest(
            @Query("id_supplier") String idSupplier
    );

    @GET("tampil_notif_supplier_item.php")
    Call<Request> getNotifSupplierItemRequest(
            @Query("id_request") String idRequest
    );

    @FormUrlEncoded
    @POST("supplier/batal_request.php")
    Call<Value> batalRequest(
            @Field("id_request") String idRequest
    );

    @FormUrlEncoded
    @POST("hapus_barang.php")
    Call<Value> hapusBarangRequest(
            @Field("id_barang") String idBarang
    );

    @GET("tampil_pesan_admin.php")
    Call<List<Pesan>> pesanListRequest();

    @GET("tampil_user.php")
    Call<User> getUserItemRequest(
            @Query("id_user") String idUser
    );

    @FormUrlEncoded
    @POST("hapus_pesan_admin.php")
    Call<Value> hapusPesanAdminRequest(
            @Field("id_pesan") String idPesan
    );

    @FormUrlEncoded
    @POST("ubah_status_user.php")
    Call<Value> ubahStatusUserRequest(
            @Field("id") String id,
            @Field("level") String level
    );

    @FormUrlEncoded
    @POST("login_admin.php")
    Call<Value> loginAdminRequest(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("edit_profil_admin.php")
    Call<Value> ediProfilAdminRequest(
            @Field("id_admin") int idAdmin,
            @Field("username") String username,
            @Field("nama") String nama,
            @Field("password") String password,
            @Field("avatar") String avatar
    );

    @GET("tampil_admin.php")
    Call<Admin> getAdminItem(
            @Query("id_admin") int idAdmin
    );
}
