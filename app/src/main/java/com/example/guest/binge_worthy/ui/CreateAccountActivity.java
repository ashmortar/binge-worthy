package com.example.guest.binge_worthy.ui;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = CreateAccountActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mName;
    private ProgressDialog mAuthDialog;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @BindView(R.id.createAccountNameEditText) EditText mNameEditText;
    @BindView(R.id.createAccountEmailEditText) EditText mEmailEditText;
    @BindView(R.id.createAccountPasswordEditText) EditText mPasswordEditText;
    @BindView(R.id.createAccountConfirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.createAccountButton) Button mCreateAccountButton;
    @BindView(R.id.logInTextView) TextView mLogInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mLogInTextView.setOnClickListener(this);
        mCreateAccountButton.setOnClickListener(this);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
        createAuthDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(buttonClick);
        if (view == mCreateAccountButton) {
            createAccount();
        }
        if (view == mLogInTextView) {
            Intent intent = new Intent(CreateAccountActivity.this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmailEditText.setError("please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidName(String name) {
        if (name.equals("")) {
            mNameEditText.setError("please enter a display name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPasswordEditText.setError("password must contain at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError("passwords must match");
            return false;
        }
        return true;
    }

    private void createAccount() {
        final String name = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        String pass = mPasswordEditText.getText().toString().trim();
        String confPass = mConfirmPasswordEditText.getText().toString().trim();
        mName = name;

        boolean emailCheck = isValidEmail(email);
        boolean nameCheck = isValidName(name);
        boolean passCheck = isValidPassword(pass, confPass);
        if (!emailCheck || !nameCheck || !passCheck) return;

        mAuthDialog.show();

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mAuthDialog.dismiss();
                if (task.isSuccessful()) {
                    addDisplayNameToFirebase(task.getResult().getUser());
                } else {
                    Log.d(TAG, "create account failed: " + task.getException());
                    Toast.makeText(CreateAccountActivity.this, "account creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addDisplayNameToFirebase(final FirebaseUser user) {
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .build();
        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "user diplay name updated to " + user.getDisplayName());
                        }
                    }
                });
    }

    private void createAuthDialog() {
        mAuthDialog = new ProgressDialog(this);
        mAuthDialog.setTitle("Please wait...");
        mAuthDialog.setMessage("authenticating user...");
        mAuthDialog.setCancelable(false);
    }
}
