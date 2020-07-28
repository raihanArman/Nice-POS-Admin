package id.co.myproject.nicepos_admin.view.transaction;


import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_admin.BuildConfig;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.adapter.PesananAdapter;
import id.co.myproject.nicepos_admin.model.Pesanan;
import id.co.myproject.nicepos_admin.model.Request;
import id.co.myproject.nicepos_admin.model.Value;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRequestFragment extends Fragment {

    TextView tv_user, tv_cafe, tv_total, tv_batalkan;
    ImageView iv_user, iv_back;
    RecyclerView rv_request;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    PesananAdapter pesananAdapter;
    ProgressDialog progressDialog;

    public DetailRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences("supplier", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");

        tv_user = view.findViewById(R.id.tv_user);
        tv_cafe = view.findViewById(R.id.tv_cafe);
        tv_total = view.findViewById(R.id.tv_total);
        tv_batalkan = view.findViewById(R.id.tv_batalkan);
        iv_user = view.findViewById(R.id.iv_user);
        iv_back = view.findViewById(R.id.iv_back);
        rv_request = view.findViewById(R.id.rv_request);
        String idRequest = getArguments().getString("id_request");
        rv_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        pesananAdapter = new PesananAdapter(getActivity());
        rv_request.setAdapter(pesananAdapter);

        loadDataRequestItem(idRequest);
        loadDataPesanan(idRequest);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        tv_batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batalRequestBtn(idRequest);
            }
        });

    }


    private void batalRequestBtn(String idRequest) {
        progressDialog.show();
        Call<Value> batalRequest = apiRequest.batalRequest(idRequest);
        batalRequest.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    if(response.body().getValue() == 1){
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                        Toast.makeText(getActivity(), "Berhasil mengudate", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Gagal mengudate", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataPesanan(String idRequest) {
        Call<List<Pesanan>> getPesanan = apiRequest.getPesananSupplierRequest(idRequest);
        getPesanan.enqueue(new Callback<List<Pesanan>>() {
            @Override
            public void onResponse(Call<List<Pesanan>> call, Response<List<Pesanan>> response) {
                if (response.isSuccessful()){
                    List<Pesanan> pesananList = response.body();
                    pesananAdapter.setPesananList(pesananList);
                }
            }

            @Override
            public void onFailure(Call<List<Pesanan>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataRequestItem(String idRequest) {
        Call<Request> notificationCall = apiRequest.getNotifSupplierItemRequest(idRequest);
        notificationCall.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()){
                    Request request = response.body();
                    setData(request);
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {

            }
        });
    }

    private void setData(Request request) {
        Glide.with(getActivity()).load(BuildConfig.BASE_URL_GAMBAR+"cafe/"+request.getGambar()).into(iv_user);
        tv_user.setText(request.getNamaUser());
        tv_cafe.setText(request.getNamaCafe());
        tv_total.setText(request.getTotalHarga());
        if (request.getStatus().equals("lunas")){
            tv_batalkan.setVisibility(View.GONE);
        }
    }
}
