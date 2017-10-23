package com.example.proyectotiti;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.text.TextUtils;
import android.util.Log;


import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userInput;
    private EditText passInput;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views
        userInput = (EditText)findViewById(R.id.usernameInput);
        passInput = (EditText)findViewById(R.id.passwordInput);

        // Buttons
        findViewById(R.id.startButton).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.forgot_password_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        Log.d("DEBUG", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DEBUG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("DEBUG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void sendPasswordReset() {
        // Disable button
        findViewById(R.id.forgot_password_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        mAuth.sendPasswordResetEmail(user.getEmail())
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.forgot_password_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("DEBUG", "sendEmailVerification", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Failed to send email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }


    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Authentication success.", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("DEBUG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
//                        }
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
//                    user.getEmail(), user.isEmailVerified()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

//            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
//            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.startButton).setVisibility(View.VISIBLE);

            findViewById(R.id.forgot_password_button).setEnabled(!user.isEmailVerified());
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);

//            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
//            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.startButton).setVisibility(View.GONE);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = userInput.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userInput.setError("Required.");
            valid = false;
        } else {
            userInput.setError(null);
        }

        String password = passInput.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passInput.setError("Required.");
            valid = false;
        } else {
            passInput.setError(null);
        }

        return valid;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.startButton) {
            signIn(userInput.getText().toString(), passInput.getText().toString());
        }
        else if(i == R.id.email_create_account_button) {
            createAccount(userInput.getText().toString(), passInput.getText().toString());
        }
        else if(i == R.id.forgot_password_button) {
            sendPasswordReset();
        }
    }

    public void openHome(View v){
        startActivity(new Intent(MainActivity.this, home.class));
    }
}
