package com.example.guest.binge_worthy.ui;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.adapters.RecommendationListAdapter;
import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.services.TasteDiveService;
import java.io.IOException;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ListFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = ListFragment.class.getSimpleName();

    private RecommendationListAdapter mAdapter;
    public ArrayList<Recommendation> recommendations = new ArrayList<>();
    private Context mContext;
    private Recommendation queryFound;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentQuery;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerVew;
    @BindView(R.id.textView2) TextView mSearchedTermView;


    public ListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();




        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);
        mContext = getContext();

        mRecentQuery = mSharedPreferences.getString("query", null);

        if (mRecentQuery != null) {
            mSearchedTermView.setText(mRecentQuery);
            mSearchedTermView.setOnClickListener(this);
            getRecommendations(mRecentQuery);
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getRecommendations(query);
                mSearchedTermView.setText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(buttonClick);
        if (view == mSearchedTermView) {

            Toast.makeText(getActivity(), queryFound.getwTeaser(), Toast.LENGTH_LONG).show();
        }
    }

    private void getRecommendations(String query) {
        final TasteDiveService tasteDiveService = new TasteDiveService();

        tasteDiveService.apiCall(query, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v(TAG, response.toString());
                recommendations = tasteDiveService.processResults(response);


                if (recommendations.size() > 0 ) {
                    queryFound = recommendations.get(0);
                    recommendations.remove(0);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new RecommendationListAdapter(getActivity(), recommendations);
                            mRecyclerVew.setAdapter(mAdapter);
                            mRecyclerVew.setHasFixedSize(false);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            Log.d(TAG, "run: " + layoutManager.isAutoMeasureEnabled());
                            mRecyclerVew.setLayoutManager(layoutManager);
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"your search did not match any results please try again", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

    }

    private void addToSharedPreferences(String query) {
        mEditor.putString("query", query).apply();
    }

}
