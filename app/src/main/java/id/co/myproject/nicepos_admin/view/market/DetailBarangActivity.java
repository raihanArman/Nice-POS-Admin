package id.co.myproject.nicepos_admin.view.market;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import id.co.myproject.nicepos_admin.BuildConfig;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.model.BarangSupplier;
import id.co.myproject.nicepos_admin.model.Supplier;
import id.co.myproject.nicepos_admin.model.Value;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import id.co.myproject.nicepos_admin.util.TimeStampFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.nicepos_admin.util.Helper.rupiahFormat;

public class DetailBarangActivity extends AppCompatActivity {

    ApiRequest apiRequest;
    ImageView iv_back, iv_barang;
    String idBarang;
    FloatingActionButton fb_cart_market;
    Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tv_supplier, tv_barang, tv_tanggal, tv_deskripsi, tv_harga, tv_hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);

        iv_back = findViewById(R.id.iv_back);
        iv_barang = findViewById(R.id.iv_barang);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        tv_supplier = findViewById(R.id.tv_supplier);
        tv_barang = findViewById(R.id.tv_barang);
        tv_tanggal = findViewById(R.id.tv_tanggal);
        tv_deskripsi = findViewById(R.id.tv_deskripsi);
        tv_harga = findViewById(R.id.tv_harga);
        tv_hapus = findViewById(R.id.tv_hapus);

        idBarang = getIntent().getStringExtra("id_barang");
        loadDataBarang();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProses();
            }
        });

    }


    private void deleteProses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin ingin menghapus barang ini ? ");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call<Value> deleteBarang = apiRequest.hapusBarangRequest(idBarang);
                deleteBarang.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(DetailBarangActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                            if (response.body().getValue() == 1){
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Toast.makeText(DetailBarangActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void loadDataBarang() {
        Call<BarangSupplier> barangSupplierCall = apiRequest.getBarangItemRequest(idBarang);
        barangSupplierCall.enqueue(new Callback<BarangSupplier>() {
            @Override
            public void onResponse(Call<BarangSupplier> call, Response<BarangSupplier> response) {
                if (response.isSuccessful()){
                    BarangSupplier barangSupplier = response.body();
                    setData(barangSupplier);
                }
            }

            @Override
            public void onFailure(Call<BarangSupplier> call, Throwable t) {
                Toast.makeText(DetailBarangActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(BarangSupplier barangSupplier) {
        TimeStampFormatter timeStampFormatter = new TimeStampFormatter();
        Glide.with(this).load(BuildConfig.BASE_URL_GAMBAR+"barang/"+barangSupplier.getGambar()).into(iv_barang);
        tv_barang.setText(barangSupplier.getNamaBarang());
        tv_deskripsi.setText(barangSupplier.getDeskripsi());
        tv_harga.setText(rupiahFormat(Integer.parseInt(barangSupplier.getHarga()))+"/"+barangSupplier.getSatuan());
        tv_tanggal.setText(timeStampFormatter.format(barangSupplier.getTanggal()));
        Call<Supplier> supplierCall = apiRequest.getSupplierItemRequest(barangSupplier.getIdSupplier());
        supplierCall.enqueue(new Callback<Supplier>() {
            @Override
            public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                if (response.isSuccessful()){
                    tv_supplier.setText(response.body().getNamaSupplier());
                }
            }

            @Override
            public void onFailure(Call<Supplier> call, Throwable t) {
                Toast.makeText(DetailBarangActivity.this, "Supplier : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
