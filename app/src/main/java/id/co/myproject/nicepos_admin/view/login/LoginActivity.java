package id.co.myproject.nicepos_admin.view.login;

import androidx.appcompat.app.AppCompatActivity;
import id.co.myproject.nicepos_admin.MainActivity;
import id.co.myproject.nicepos_admin.R;
import id.co.myproject.nicepos_admin.model.Value;
import id.co.myproject.nicepos_admin.request.ApiRequest;
import id.co.myproject.nicepos_admin.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;
    TextView btn_login;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    public static final String KEY_LOGIN_STATUS = "login_status";
    public static final String KEY_ID_ADMIN = "id_admin";
    public static final String KEY_LOGIN_SHARED_PREF = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiRequest = RetrofitRequest.getRetrofitInstace().create(ApiRequest.class);
        sharedPreferences = getSharedPreferences(KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses ...");

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProses();
            }
        });
        
    }

    private void loginProses() {
        progressDialog.show();
        Call<Value> loginRequest = apiRequest.loginAdminRequest(et_username.getText().toString(),et_password.getText().toString());
        loginRequest.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    if (response.body().getValue() == 1){
                        editor.putBoolean(KEY_LOGIN_STATUS, true);
                        editor.putInt(KEY_ID_ADMIN, response.body().getIdAdmin());
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
