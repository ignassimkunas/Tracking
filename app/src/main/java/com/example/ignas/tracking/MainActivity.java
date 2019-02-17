package com.example.ignas.tracking;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    static DatabaseReference databaseReference;
    BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.statistics:
                            selectedFragment = new Statistics();
                            break;
                        case R.id.topay:
                            selectedFragment = new ToPay();
                            break;
                        case R.id.users:
                            selectedFragment = new Users();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                            selectedFragment).commit();
                    return true;


                }
            };
    private FirebaseAuth mAuth;

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "trax";
            String description = "New month";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("trax", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        BottomNavigationView bottomNavigationView;

        createNotificationChannel();

        databaseReference = FirebaseDatabase.getInstance().getReference("month");

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = findViewById(R.id.relative);

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                new ToPay()).commit();

    }

}
