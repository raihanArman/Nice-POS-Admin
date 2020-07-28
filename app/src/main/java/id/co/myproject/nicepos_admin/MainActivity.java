package id.co.myproject.nicepos_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.nicepos_admin.view.notif.NotifFragment;
import id.co.myproject.nicepos_admin.view.profil.ProfilActivity;
import id.co.myproject.nicepos_admin.view.profil.ProfilFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("");


        frameLayout = findViewById(R.id.fm_home);

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_apps_black_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_notifications_black_24dp));

        setFrament(new HomeFragment());

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
//                Intent intent = new Intent(MainActivity.this, InputBarangActivity.class);
//                intent.putExtra("type_intent", TYPE_INTENT_ADD);
//                startActivity(intent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0){
                    if (!getSupportActionBar().isShowing()){
                        getSupportActionBar().show();
                    }
                    setFrament(new HomeFragment());
                }else if (itemIndex == 1){
                    if (!getSupportActionBar().isShowing()){
                        getSupportActionBar().show();
                    }
                    setFrament(new NotifFragment());
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });

    }

    private void setFrament(Fragment frament){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), frament);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            getSupportFragmentManager().popBackStackImmediate();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");
        }else if (id == R.id.menu_profile){
            Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
