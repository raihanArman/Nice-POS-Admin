package id.co.myproject.nicepos_admin.view.notif;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_admin.MainActivity;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.adapter.NotifAdapter;
import id.co.myproject.nicepos_admin.model.Pesan;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import id.co.myproject.nicepos_admin.util.TimeStampFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifFragment extends Fragment {

    RecyclerView rv_notif;
    ApiRequest apiRequest;
    NotifAdapter notifAdapter;

    public NotifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notif, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Notifikasi");

        rv_notif = view.findViewById(R.id.rv_notif);
        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);

        notifAdapter = new NotifAdapter(getActivity(), apiRequest, new TimeStampFormatter());
        rv_notif.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_notif.setAdapter(notifAdapter);
        loadData();
    }

    private void loadData() {
        Call<List<Pesan>> getPesanAdminRequest = apiRequest.pesanListRequest();
        getPesanAdminRequest.enqueue(new Callback<List<Pesan>>() {
            @Override
            public void onResponse(Call<List<Pesan>> call, Response<List<Pesan>> response) {
                if (response.isSuccessful()){
                    List<Pesan> pesanList = response.body();
                    notifAdapter.setPesanList(pesanList);
                }
            }

            @Override
            public void onFailure(Call<List<Pesan>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
