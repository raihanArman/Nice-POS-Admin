package id.co.myproject.nicepos_admin.view.market;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_admin.MainActivity;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.adapter.MarketAdapter;
import id.co.myproject.nicepos_admin.adapter.SupplierListAdapter;
import id.co.myproject.nicepos_admin.model.BarangSupplier;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {

    ApiRequest apiRequest;
    RecyclerView rv_barang_list;
    MarketAdapter marketAdapter;

    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Barang list");

        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);

        rv_barang_list = view.findViewById(R.id.rv_barang_list);

        marketAdapter = new MarketAdapter(getActivity());
        rv_barang_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_barang_list.setAdapter(marketAdapter);

        loadDataMarket();
    }


    private void loadDataMarket() {

        Call<List<BarangSupplier>> getMarket = apiRequest.getBarangRequest();
        getMarket.enqueue(new Callback<List<BarangSupplier>>() {
            @Override
            public void onResponse(Call<List<BarangSupplier>> call, Response<List<BarangSupplier>> response) {
                if (response.isSuccessful()){
                    List<BarangSupplier> barangSupplierList = response.body();
                    marketAdapter.setBarangSupplierList(barangSupplierList);
                }
            }

            @Override
            public void onFailure(Call<List<BarangSupplier>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
