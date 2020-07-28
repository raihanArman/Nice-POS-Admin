package id.co.myproject.nicepos_admin.view.transaction;


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
import id.co.myproject.nicepos_admin.adapter.TransaksiListAdapter;
import id.co.myproject.nicepos_admin.model.Request;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionListFragment extends Fragment {

    ApiRequest apiRequest;
    RecyclerView rv_transaksi;
    TransaksiListAdapter transaksiListAdapter;

    public TransactionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Transaksi list");

        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);
        rv_transaksi = view.findViewById(R.id.rv_transaksi_list);

        transaksiListAdapter = new TransaksiListAdapter(getActivity(), apiRequest);
        rv_transaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_transaksi.setAdapter(transaksiListAdapter);

        loadDataTransaksi();


//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().popBackStackImmediate();
//            }
//        });

    }

    private void loadDataTransaksi() {
        Call<List<Request>> requestCall = apiRequest.getTransaksiListRequest();
        requestCall.enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                if (response.isSuccessful()){
                    List<Request> requestList = response.body();
                    transaksiListAdapter.setRequestList(requestList);
                }
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
