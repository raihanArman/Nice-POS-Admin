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
import id.co.myproject.nicepos_admin.model.Supplier;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.ViewHolder> {

    List<Supplier> supplierList = new ArrayList<>();
    Context context;

    public SupplierListAdapter(Context context) {
        this.context = context;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList.clear();
        this.supplierList.addAll(supplierList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SupplierListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cafe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierListAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"supplier/"+supplierList.get(position).getAvatar()).into(holder.ivSupplier);
        holder.tvSupplier.setText(supplierList.get(position).getNamaSupplier());
        holder.tvAlamat.setText(supplierList.get(position).getAlamat());
    }

    @Override
    public int getItemCount() {
        return supplierList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivSupplier;
        TextView tvSupplier, tvAlamat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSupplier = itemView.findViewById(R.id.iv_cafe);
            tvSupplier = itemView.findViewById(R.id.tv_nama_cafe);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
        }
    }
}
