package id.co.myproject.nicepos_admin.view.cafe;


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
import id.co.myproject.nicepos_admin.adapter.CafListAdapter;
import id.co.myproject.nicepos_admin.model.Cafe;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CafeListFragment extends Fragment {

    ApiRequest apiRequest;
    RecyclerView rv_cafe_list;
    CafListAdapter cafListAdapter;

    public CafeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cafe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Cafe list");

        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);

        rv_cafe_list = view.findViewById(R.id.rv_cafe_list);

        cafListAdapter = new CafListAdapter(getActivity());
        rv_cafe_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_cafe_list.setAdapter(cafListAdapter);

        loadDataList();


    }

    private void loadDataList() {
        Call<List<Cafe>> cafeCall = apiRequest.getCafeListRequest();
        cafeCall.enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                if (response.isSuccessful()){
                    List<Cafe> cafeList = response.body();
                    cafListAdapter.setCafeList(cafeList);
                }
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
