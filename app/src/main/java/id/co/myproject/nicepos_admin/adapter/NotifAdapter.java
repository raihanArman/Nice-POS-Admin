package id.co.myproject.nicepos_admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.model.Cafe;
import id.co.myproject.nicepos_admin.model.Pesan;
import id.co.myproject.nicepos_admin.model.Supplier;
import id.co.myproject.nicepos_admin.model.User;
import id.co.myproject.nicepos_admin.model.Value;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.util.TimeStampFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {
    List<Pesan> pesanList = new ArrayList<>();
    Context context;
    ApiRequest apiRequest;
    TimeStampFormatter timeStampFormatter;

    public NotifAdapter(Context context, ApiRequest apiRequest, TimeStampFormatter timeStampFormatter) {
        this.context = context;
        this.apiRequest = apiRequest;
        this.timeStampFormatter = timeStampFormatter;
    }

    public void setPesanList(List<Pesan> pesanList){
        this.pesanList.clear();
        this.pesanList.addAll(pesanList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.ViewHolder holder, int position) {
        holder.tvUser.setText(pesanList.get(position).getNamaUser());
        holder.tvEmail.setText(pesanList.get(position).getEmail());
        holder.tvLevel.setText(pesanList.get(position).getLevel());
        holder.tvIsi.setText(pesanList.get(position).getIsi());
        holder.tvTanggal.setText(timeStampFormatter.format(pesanList.get(position).getTanggal()));
        if(pesanList.get(position).getLevel().equals("cafe")){
            holder.ivUser.setImageDrawable(context.getDrawable(R.drawable.cafe_icon));
            Call<User> cafeCall = apiRequest.getUserItemRequest(pesanList.get(position).getIdUser());
            cafeCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()){
                        User user = response.body();
                        if (user.getStatus().equals("belum dikonfirmasi")){
                            holder.tvStatus.setTextColor(context.getColor(R.color.red_main));
                            holder.lvConfirm.setVisibility(View.VISIBLE);
                            holder.tvIsi.setVisibility(View.VISIBLE);
                            holder.tvStatus.setText("Status : "+user.getStatus());
                        }else {
                            holder.tvStatus.setText("Status : "+user.getStatus());
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else if (pesanList.get(position).getLevel().equals("supplier")){
            holder.ivUser.setImageDrawable(context.getDrawable(R.drawable.supplier_icon));
            Call<Supplier> supplierCall = apiRequest.getSupplierItemRequest(pesanList.get(position).getIdUser());
            supplierCall.enqueue(new Callback<Supplier>() {
                @Override
                public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                    if (response.isSuccessful()){
                        Supplier supplier = response.body();
                        if (supplier.getStatus().equals("belum dikonfirmasi")){
                            holder.tvStatus.setTextColor(context.getColor(R.color.red_main));
                            holder.lvConfirm.setVisibility(View.VISIBLE);
                            holder.tvIsi.setVisibility(View.VISIBLE);
                            holder.tvStatus.setText("Status : "+supplier.getStatus());
                        }else {
                            holder.tvStatus.setVisibility(View.VISIBLE);
                            holder.tvStatus.setText("Status : "+supplier.getStatus());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Supplier> call, Throwable t) {
                    Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        holder.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Value> ubahStatusRequest = apiRequest.ubahStatusUserRequest(pesanList.get(position).getIdUser(), pesanList.get(position).getLevel());
                ubahStatusRequest.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if(response.body().getValue() == 1){
                                holder.tvStatus.setText("Status : sudah dikonfirmasi");
                                holder.tvStatus.setTextColor(context.getColor(R.color.colorRose));
                                holder.lvConfirm.setVisibility(View.GONE);
                                holder.tvStatus.setVisibility(View.VISIBLE);
                            }else {
                                holder.lvConfirm.setVisibility(View.VISIBLE);
                                holder.tvStatus.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Value> hapusPesanAdminRequest = apiRequest.hapusPesanAdminRequest(pesanList.get(position).getIdPesan());
                hapusPesanAdminRequest.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if(response.body().getValue() == 1){
                                removeList(position);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return pesanList.size();
    }

    public void removeList(int position){
        pesanList.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivUser;
        TextView tvUser, tvEmail, tvDelete, tvConfirm, tvLevel, tvIsi, tvTanggal, tvStatus;
        LinearLayout lvConfirm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.iv_user);
            tvUser = itemView.findViewById(R.id.tv_nama_user);
            tvEmail = itemView.findViewById(R.id.tv_email_user);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvConfirm = itemView.findViewById(R.id.tv_confirm);
            tvLevel = itemView.findViewById(R.id.tv_level);
            tvIsi = itemView.findViewById(R.id.tv_isi);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            lvConfirm = itemView.findViewById(R.id.lv_confirm);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
