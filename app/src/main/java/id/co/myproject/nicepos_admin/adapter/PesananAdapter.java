package id.co.myproject.nicepos_admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.model.Pesanan;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.ViewHolder> {

    List<Pesanan> pesananList = new ArrayList<>();
    Context context;

    public PesananAdapter(Context context) {
        this.context = context;
    }

    public void setPesananList(List<Pesanan> pesananList){
        this.pesananList.clear();
        this.pesananList.addAll(pesananList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PesananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananAdapter.ViewHolder holder, int position) {
        holder.tvBarang.setText(pesananList.get(position).getNamaBarang());
        holder.tvSubTotal.setText(pesananList.get(position).getSubTotal());
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvBarang, tvSubTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBarang = itemView.findViewById(R.id.tv_barang);
            tvSubTotal = itemView.findViewById(R.id.tv_sub_total);
        }
    }
}
