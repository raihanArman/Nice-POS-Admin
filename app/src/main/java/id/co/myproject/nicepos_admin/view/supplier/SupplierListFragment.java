package id.co.myproject.nicepos_admin.view.supplier;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_admin.MainActivity;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.adapter.SupplierListAdapter;
import id.co.myproject.nicepos_admin.model.Supplier;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierListFragment extends Fragment {
    ApiRequest apiRequest;
    RecyclerView rv_supplier_list;
    SupplierListAdapter supplierListAdapter;

    public SupplierListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supplier_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Supplier list");

        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);

        rv_supplier_list = view.findViewById(R.id.rv_supplier_list);

        supplierListAdapter = new SupplierListAdapter(getActivity());
        rv_supplier_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_supplier_list.setAdapter(supplierListAdapter);

        loadDataSupplier();


    }

    private void loadDataSupplier() {
        Call<List<Supplier>> supplierCall = apiRequest.getSupplierListRequest();
        supplierCall.enqueue(new Callback<List<Supplier>>() {
            @Override
            public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                if (response.isSuccessful()){
                    List<Supplier> supplierList = response.body();
                    supplierListAdapter.setSupplierList(supplierList);
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
