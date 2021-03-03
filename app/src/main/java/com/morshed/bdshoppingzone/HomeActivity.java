package com.morshed.bdshoppingzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.morshed.bdshoppingzone.Fragment.CartFragment;
import com.morshed.bdshoppingzone.Fragment.ContactFragment;
import com.morshed.bdshoppingzone.Fragment.DashbordFragment;
import com.morshed.bdshoppingzone.Fragment.LoginFragment;

public class HomeActivity extends AppCompatActivity {

    //Initialize for Bottom Navigation----------
    BottomNavigationView bottomNavigationView;

    //Initialize for Fragment-------------------
    FrameLayout frameLayout;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set No Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //---------------------------//
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

//        FirebaseMessaging.getInstance().subscribeToTopic("Notification");
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        //Call BottomNavigation Click Method---------------
        BottomNavigationClickMethod();
        //Call BottomNavigation Default Click Method------
        BottomNavigationDefaultMethod();

        subscribeToTopic();
        getToken();

        requestNotificationPermission();
    }


    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, 1 );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // Checking the request code of our request
        if (requestCode == 1 ) {

            // If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                // Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Bottom Navigation Click Method
    private void BottomNavigationClickMethod(){

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment fragment = null;
                switch (id)
                {
                    case R.id.bottom_nav_home:
                        fragment = new DashbordFragment();
                        break;
                    case R.id.bottom_nav_cart:
                        fragment = new CartFragment();
//                        startActivity(new Intent(HomeActivity.this, ScanActivity.class));
                        break;
                    case R.id.bottom_nav_contact:
                        fragment = new ContactFragment();
//                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;
                    case R.id.bottom_nav_login:
                        fragment = new LoginFragment();
//                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;
                }
                if (fragment != null)
                {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,fragment)
                            .commit();
                }
                else
                {
                    Log.e("Error ","Error");
                }
                return true;
            }
        });


    }

    // Bottom Navigation Default Click Method
    public void BottomNavigationDefaultMethod()
    {

        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        fragmentManager = getSupportFragmentManager();
        DashbordFragment homeFragment = new DashbordFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,homeFragment)
                .commit();
    }

    private void subscribeToTopic()
    {
        FirebaseMessaging.getInstance().subscribeToTopic("notification")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d("TAG", msg);
//                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getToken()
    {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.e("Token",instanceIdResult.getToken());
                    }
                });
    }
}