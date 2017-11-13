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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.proyectotiti.models.User;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";


    private EditText userInput;
    private EditText passInput;
    private Button SignInButton;
    private Button SignUpButton;
    private Button ForgotPasswordButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        userInput = (EditText)findViewById(R.id.usernameInput);
        passInput = (EditText)findViewById(R.id.passwordInput);
        SignInButton = (Button)findViewById(R.id.startButton);
        SignUpButton = (Button)findViewById(R.id.email_create_account_button);
        ForgotPasswordButton = (Button)findViewById(R.id.forgot_password_button);

        // Buttons
        SignInButton.setOnClickListener(this);
        SignUpButton.setOnClickListener(this);
        ForgotPasswordButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                hideProgressDialog();

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    onAuthSuccess(task.getResult().getUser());
                    Toast.makeText(getApplicationContext(), "Authentication success.", Toast.LENGTH_SHORT).show();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("DEBUG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            onAuthSuccess(task.getResult().getUser());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("DEBUG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void sendPasswordReset() {
        // Disable button
        findViewById(R.id.forgot_password_button).setEnabled(false);

        // Send reset password email
        final FirebaseUser user = mAuth.getCurrentUser();
        mAuth.sendPasswordResetEmail(user.getEmail())
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to home
        startActivity(new Intent(MainActivity.this, home.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
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

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
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
}
