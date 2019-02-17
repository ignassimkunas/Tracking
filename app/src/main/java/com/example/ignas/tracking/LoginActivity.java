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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public void login(View view) {

        final EditText emailView = findViewById(R.id.username);
        EditText passwordView = findViewById(R.id.password);

        final String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        if (!email.isEmpty() && !password.isEmpty()){

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this, "Login successful, Welcome " + email, Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

        }

    }

    public void toSignUp(View view) {

        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        if (currentUser != null) {

            startActivity(intent);

        }

    }
}
