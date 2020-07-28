package id.co.myproject.nicepos_admin.view.profil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.nicepos_admin.R;

import android.os.Bundle;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fm_profil, new ProfilFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
