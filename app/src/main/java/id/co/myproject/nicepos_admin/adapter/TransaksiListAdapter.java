package id.co.myproject.nicepos_admin.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_admin.BuildConfig;
import id.co.myproject.nicepos_admin.MainActivity;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.model.Pesanan;
import id.co.myproject.nicepos_admin.model.Request;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.view.transaction.DetailRequestFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiListAdapter extends RecyclerView.Adapter<TransaksiListAdapter.ViewHolder> {

    List<Request> requestList = new ArrayList<>();
    Context context;
    ApiRequest apiRequest;

    public TransaksiListAdapter(Context context, ApiRequest apiRequest) {
        this.context = context;
        this.apiRequest = apiRequest;
    }

    public void setRequestList(List<Request> requestList){
        this.requestList.clear();
        this.requestList.addAll(requestList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransaksiListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiListAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"cafe/"+requestList.get(position).getGambar()).into(holder.ivCafe);
        holder.tvCafe.setText(requestList.get(position).getNamaUser()+" - "+requestList.get(position).getNamaCafe());
        holder.tvSupplier.setText("to : "+requestList.get(position).getNamaSupplier());
        holder.tvTanggal.setText(DateFormat.format("dd MMM yyyy HH:mm", requestList.get(position).getTanggal()));
        holder.tvTotal.setText("Total "+requestList.get(position).getTotalHarga());
        holder.tvStatus.setText("Status : "+requestList.get(position).getStatus());

        PesananAdapter pesananAdapter = new PesananAdapter(context);
        holder.rv_pesanan.setLayoutManager(new LinearLayoutManager(context));
        holder.rv_pesanan.setAdapter(pesananAdapter);

        Call<List<Pesanan>> pesananCall = apiRequest.getPesananSupplierRequest(requestList.get(position).getIdRequest());
        pesananCall.enqueue(new Callback<List<Pesanan>>() {
            @Override
            public void onResponse(Call<List<Pesanan>> call, Response<List<Pesanan>> response) {
                if (response.isSuccessful()) {
                    List<Pesanan> pesananList = response.body();
                    pesananAdapter.setPesananList(pesananList);
                }
            }

            @Override
            public void onFailure(Call<List<Pesanan>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailRequestFragment detailRequestFragment = new DetailRequestFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id_request", requestList.get(position).getIdRequest());
                detailRequestFragment.setArguments(bundle);
                ((MainActivity)view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fm_home, detailRequestFragment)
                        .commit();
            }
        });



    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCafe, tvSupplier, tvTanggal, tvTotal, tvStatus;
        ImageView ivCafe, ivArrow;
        RecyclerView rv_pesanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCafe = itemView.findViewById(R.id.tv_cafe);
            tvSupplier = itemView.findViewById(R.id.tv_supplier);
            ivCafe = itemView.findViewById(R.id.iv_cafe);
            rv_pesanan = itemView.findViewById(R.id.rv_pesanan);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvStatus = itemView.findViewById(R.id.tv_status);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
        }
    }
}
