package id.co.myproject.nicepos_admin.view.profil;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.co.myproject.nicepos_admin.BuildConfig;
import id.co.myproject.nicepos_admin.MainActivity;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.model.Admin;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import id.co.myproject.nicepos_admin.view.login.LoginActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.nicepos_admin.view.login.LoginActivity.KEY_ID_ADMIN;
import static id.co.myproject.nicepos_admin.view.login.LoginActivity.KEY_LOGIN_SHARED_PREF;
import static id.co.myproject.nicepos_admin.view.login.LoginActivity.KEY_LOGIN_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    ImageView ivUser, ivBackground, ivSetting, ivBack;
    TextView tvUser, tvEmail;
    Button btnLogOut;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiRequest apiRequest;
    int idAdmin;
    public static boolean statusUpdate = false;

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences(KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        idAdmin = sharedPreferences.getInt(KEY_ID_ADMIN, 0);

        ivUser = view.findViewById(R.id.iv_user);
        ivBack = view.findViewById(R.id.iv_back);
        ivSetting = view.findViewById(R.id.iv_setting);
        ivBackground = view.findViewById(R.id.iv_background);
        tvUser = view.findViewById(R.id.tv_user);
        tvEmail = view.findViewById(R.id.tv_email);
        btnLogOut = view.findViewById(R.id.btn_log_out);
        ivSetting.setVisibility(View.VISIBLE);
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfilFragment editProfilFragment = new EditProfilFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id_admin", idAdmin);
                editProfilFragment.setArguments(bundle);
                ((ProfilActivity)view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fm_profil, editProfilFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


        loadDataAdmin();

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        editor.putBoolean(KEY_LOGIN_STATUS, false);
        editor.putInt(KEY_ID_ADMIN, 0);
        editor.commit();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loadDataAdmin(){
        Call<Admin> adminCall = apiRequest.getAdminItem(idAdmin);
        adminCall.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if (response.isSuccessful()){
                    Admin admin = response.body();
                    String avatar = admin.getAvatar();
                    String image = "";
                    tvUser.setText(admin.getNama());
                    tvEmail.setText(admin.getUsername());
                    if(avatar.equals("")){
                        image = BuildConfig.BASE_URL_GAMBAR+"user/avatar.jpg";
                    }else {
                        image = BuildConfig.BASE_URL_GAMBAR+"user/"+avatar;
                    }
                    Glide.with(getActivity()).load(image).into(ivUser);
                    Glide.with(getActivity()).load(image).transform(new BlurTransformation(25,3)).into(ivBackground);
                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (statusUpdate) {
            loadDataAdmin();
        }

    }
}
