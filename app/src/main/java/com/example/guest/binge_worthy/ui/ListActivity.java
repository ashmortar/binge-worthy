package com.example.guest.binge_worthy.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.guest.binge_worthy.R;


public class ListActivity extends AppCompatActivity {
    public static final String TAG = ListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
}
