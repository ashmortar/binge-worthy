package com.example.guest.binge_worthy.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @BindView(R.id.logo1) TextView mLogo1;
    @BindView(R.id.logo2) TextView mLogo2;
    @BindView(R.id.logo3) TextView mLogo3;
    @BindView(R.id.textView4) TextView mTagline;
    @BindView(R.id.searchQuery) EditText mSearchQuery;
    @BindView(R.id.queryButton) Button mQueryButton;
    @BindView(R.id.savedItemsList) Button mSavedItemsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSearchQuery.setText("");
        mQueryButton.setOnClickListener(this);
        mSavedItemsButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "onCreate: has run");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Hello, " + user.getDisplayName());
                } else {
                    Intent intent  = new Intent(MainActivity.this, LogInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @
    Override
    public void onClick(View v) {
        v.startAnimation(buttonClick);
        if (v == mQueryButton) {
            String query = mSearchQuery.getText().toString();

            //validate input
            if (query.equals("")) {
                //tell user what sorts of searches can work
                Toast.makeText(this, "please enter a search query. \nTry a show, movie or band you like!", Toast.LENGTH_LONG).show();
            } else {
                //go to list activity
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("query", query);
                mSearchQuery.setText("");
                startActivity(intent);
            }
        }
        if (v == mSavedItemsButton) {
            Intent intent = new Intent(MainActivity.this, SavedRecommendationsActivity.class);
            startActivity(intent);

        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
