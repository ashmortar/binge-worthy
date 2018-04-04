package com.example.guest.binge_worthy.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.binge_worthy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private static final String TAG = LogInActivity.class.getSimpleName();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @BindView(R.id.signInEmailEditText) EditText mEmailEditText;
    @BindView(R.id.signInPasswordEditText) EditText mPasswordEditText;
    @BindView(R.id.createAccountTextView) TextView mCrreateAccounText;
    @BindView(R.id.signInButton) Button mSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "onCreate: has run");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mSignInButton.setOnClickListener(this);
        mCrreateAccounText.setOnClickListener(this);
        createAuthDialog();
    }
    private void createAuthDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Please wait...");
        mAuthProgressDialog.setMessage("authenticating user...");
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(buttonClick);
        if (view == mSignInButton) {
            login();
        }
        if (view == mCrreateAccounText) {
            Intent intent = new Intent(LogInActivity.this, CreateAccountActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void login() {
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        if (email.equals("")) {
            mEmailEditText.setError("please enter an email");
            return;
        }

        if (password.equals("")) {
            mPasswordEditText.setError("please enter a password");
            return;
        }

        mAuthProgressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        Log.d(TAG, "login attempt: " + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "login failed: " + task.getException());
                            Toast.makeText(LogInActivity.this, "login attempt failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: has run");
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: has run");
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
