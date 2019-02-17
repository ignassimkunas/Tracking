package com.example.ignas.tracking;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ToPay extends Fragment {

    static ListView listView;
    static List<Months> monthsList;
    static MonthList adapter;
    DatabaseReference databaseReference;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.todo_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        if (item.getItemId() == R.id.logout) {

            mAuth.signOut();
            startActivity(intent);

        }

        return true;
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void showNotification(){

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity(), "trax")
                .setSmallIcon(R.drawable.ic_euro)
                .setContentTitle("New taxes ready to be paid!")
                .setContentText("A new month has arrived, get your money ready!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        notificationManager.notify(0, mBuilder.build());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_to_do, container, false);

        setHasOptionsMenu(true);
        
        if (!haveNetworkConnection()){

            Toast.makeText(getActivity(), "No network connection available", Toast.LENGTH_SHORT).show();
            
        }

        listView = v.findViewById(R.id.listView);

        final Calendar cal = Calendar.getInstance();

        final String[] monthNames = new String[]{

                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getUid() != null) {

            databaseReference = FirebaseDatabase.getInstance().getReference("month").child(auth.getUid());

        }

        monthsList = new ArrayList<>();

        adapter = new MonthList(getActivity(), monthsList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                monthsList.clear();

                for (DataSnapshot monthsSnapshot : dataSnapshot.getChildren()) {

                    Months months = monthsSnapshot.getValue(Months.class);

                    monthsList.add(months);

                }

                Collections.reverse(monthsList);

                listView.setAdapter(adapter);

                if (!Objects.equals(monthNames[cal.get(Calendar.MONTH)], monthsList.get(0).name)) {

                    String id = Integer.toString(monthsList.size());

                    Months months = new Months(monthNames[cal.get(Calendar.MONTH)], id, cal.get(Calendar.YEAR), false,0,0,0,0,0,0,0);
                    databaseReference.child(months.id).setValue(months);

                    listView.setAdapter(adapter);

                    showNotification();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "You have no internet connection", Toast.LENGTH_SHORT).show();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), UploadPictureActivity.class);

                intent.putExtra("position", position);

                startActivity(intent);

            }
        });

        return v;
    }
}
