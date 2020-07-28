package id.co.myproject.nicepos_admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_admin.BuildConfig;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.model.Cafe;

public class CafListAdapter extends RecyclerView.Adapter<CafListAdapter.ViewHolder> {

    List<Cafe> cafeList = new ArrayList<>();
    Context context;

    public CafListAdapter(Context context) {
        this.context = context;
    }

    public void setCafeList(List<Cafe> cafeList){
        this.cafeList.clear();
        this.cafeList.addAll(cafeList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CafListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cafe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CafListAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"cafe/"+cafeList.get(position).getGambar()).into(holder.ivCafe);
        holder.tvCafe.setText(cafeList.get(position).getNamaCafe());
        holder.tvAlamat.setText(cafeList.get(position).getAlamatCafe());
    }

    @Override
    public int getItemCount() {
        return cafeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCafe;
        TextView tvCafe, tvAlamat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCafe = itemView.findViewById(R.id.iv_cafe);
            tvCafe = itemView.findViewById(R.id.tv_nama_cafe);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
        }
    }
}
