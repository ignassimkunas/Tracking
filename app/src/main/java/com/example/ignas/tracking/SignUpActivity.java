package com.example.ignas.tracking;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    static DatabaseReference databaseReference;
    static Calendar cal;
    static String[] monthNames;
    private FirebaseAuth mAuth;

    public static void addData() throws NullPointerException {

        cal = Calendar.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("month");

        int currentMonth = cal.get(Calendar.MONTH);

        int prevMonth1, prevMonth2;

        if (currentMonth == 0) {

            prevMonth1 = 11;
            prevMonth2 = 10;

        } else if (currentMonth == 1) {

            prevMonth1 = 0;
            prevMonth2 = 11;

        } else {

            prevMonth1 = currentMonth - 1;
            prevMonth2 = currentMonth - 2;

        }

        Months months = new Months(monthNames[prevMonth2], "0", cal.get(Calendar.YEAR), false, 0, 0, 0, 0, 0, 0, 0);
        Months months1 = new Months(monthNames[prevMonth1], "1", cal.get(Calendar.YEAR),false, 0, 0, 0, 0, 0, 0, 0);
        Months months2 = new Months(monthNames[currentMonth], "2", cal.get(Calendar.YEAR),false, 0, 0, 0, 0, 0, 0,0);

        databaseReference.child(user.getUid()).child(Integer.toString(0)).setValue(months);
        databaseReference.child(user.getUid()).child(Integer.toString(1)).setValue(months1);
        databaseReference.child(user.getUid()).child(Integer.toString(2)).setValue(months2);

    }

    String email;
    String password;

    public void logIn () {

        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "Welcome " + email, Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void signUp(View view) throws NullPointerException {

        EditText emailView = findViewById(R.id.username);
        EditText passwordView = findViewById(R.id.password);
        EditText repeatView = findViewById(R.id.repeatPass);

        email = emailView.getText().toString();
        password = passwordView.getText().toString();
        String repeatPass = repeatView.getText().toString();

        if (password.equals(repeatPass)) {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                addData();
                                logIn();
                            }

                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

        }
        else {

            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseReference = FirebaseDatabase.getInstance().getReference("month");

        monthNames = new String[]{

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

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        mAuth = FirebaseAuth.getInstance();


    }
}
