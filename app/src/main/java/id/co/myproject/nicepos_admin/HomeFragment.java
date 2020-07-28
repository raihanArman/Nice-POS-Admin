package id.co.myproject.nicepos_admin;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import id.co.myproject.nicepos_admin.model.AdminHome;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import id.co.myproject.nicepos_admin.util.TimeStampFormatter;
import id.co.myproject.nicepos_admin.view.cafe.CafeListFragment;
import id.co.myproject.nicepos_admin.view.market.MarketFragment;
import id.co.myproject.nicepos_admin.view.supplier.SupplierListFragment;
import id.co.myproject.nicepos_admin.view.transaction.TransactionListFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static id.co.myproject.nicepos_admin.view.login.LoginActivity.KEY_ID_ADMIN;
import static id.co.myproject.nicepos_admin.view.login.LoginActivity.KEY_LOGIN_SHARED_PREF;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView tv_cafe, tv_supplier, tv_transaksi, tv_tanggal_cafe, tv_tanggal_supplier, tv_tanggal_transaksi, tv_barang, tv_tanggal_barang;
    ApiRequest apiRequest;
    LinearLayout lv_cafe, lv_supplier, lv_transaksi, lv_market;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int idAdmin;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);

        sharedPreferences = getActivity().getSharedPreferences(KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        idAdmin = sharedPreferences.getInt(KEY_ID_ADMIN, 0);

        tv_cafe = view.findViewById(R.id.tv_cafe);
        tv_tanggal_cafe = view.findViewById(R.id.tv_tanggal_cafe);
        tv_supplier = view.findViewById(R.id.tv_supplier);
        tv_tanggal_supplier = view.findViewById(R.id.tv_tanggal_suppplier);
        tv_transaksi = view.findViewById(R.id.tv_transaksi);
        tv_barang = view.findViewById(R.id.tv_barang);
        tv_tanggal_transaksi = view.findViewById(R.id.tv_tanggal_transaksi);
        tv_tanggal_barang = view.findViewById(R.id.tv_tanggal_barang);
        lv_cafe = view.findViewById(R.id.lv_cafe);
        lv_supplier = view.findViewById(R.id.lv_supplier);
        lv_transaksi = view.findViewById(R.id.lv_transaksi);
        lv_market = view.findViewById(R.id.lv_market);

        lv_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(view, new CafeListFragment());
            }
        });

        lv_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(view, new SupplierListFragment());
            }
        });

        lv_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(view, new TransactionListFragment());
            }
        });

        lv_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(view, new MarketFragment());
            }
        });

        loadDataHome();

    }

    private void loadDataHome() {
        Call<AdminHome> getDataHome = apiRequest.getHomeAdminRequest();
        getDataHome.enqueue(new Callback<AdminHome>() {
            @Override
            public void onResponse(Call<AdminHome> call, Response<AdminHome> response) {
                if (response.isSuccessful()){
                    loadData(response.body());
                }
            }

            @Override
            public void onFailure(Call<AdminHome> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(AdminHome adminHome) {
        tv_cafe.setText(adminHome.getJumlahCafe()+" Cafe");
        tv_supplier.setText(adminHome.getJumlahSupplier()+" Supplier");
        tv_transaksi.setText(adminHome.getJumlahTransaksi()+" Transaksi");
        tv_barang.setText(adminHome.getJumlahBarang()+" Barang");

        TimeStampFormatter timeStampFormatter = new TimeStampFormatter();
        tv_tanggal_cafe.setText(timeStampFormatter.format(adminHome.getTanggalCafe()));
        tv_tanggal_supplier.setText(timeStampFormatter.format(adminHome.getTanggalSupplier()));
        tv_tanggal_transaksi.setText(timeStampFormatter.format(adminHome.getTanggalTransaksi()));
        tv_tanggal_barang.setText(timeStampFormatter.format(adminHome.getTanggalBarang()));

    }

    private void setFragment(View view, Fragment fragment){
        ((MainActivity)view.getContext()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fm_home, fragment)
                .addToBackStack(null)
                .commit();
    }
}
