package com.example.guest.binge_worthy.ui;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.adapters.FirebaseRecommendationViewHolder;
import com.example.guest.binge_worthy.adapters.MyFireBaseRecyclerAdapter;
import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.util.OnFirebaseDataChanged;
import com.example.guest.binge_worthy.util.OnSelectedListener;
import com.example.guest.binge_worthy.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedListFragment extends Fragment implements OnFirebaseDataChanged {
    private static final String TAG = SavedListFragment.class.getSimpleName();
    private MyFireBaseRecyclerAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private Query query;
    private String uid;
    private OnSelectedListener mListener;

    @BindView(R.id.recyclerView1) RecyclerView mRecyclerView;

    public SavedListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_list, container, false);
        ButterKnife.bind(this, view);
        setupFirebaseAdapter();
        return view;
    }

    private void setupFirebaseAdapter() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        query = FirebaseDatabase.getInstance()
                .getReference("users").child(uid)
                .orderByChild("index");
        FirebaseRecyclerOptions<Recommendation> options = new FirebaseRecyclerOptions.Builder<Recommendation>()
                .setQuery(query, Recommendation.class)
                .build();
        mFirebaseAdapter = new MyFireBaseRecyclerAdapter(options)
         {
            @Override
            public void onBindViewHolder(FirebaseRecommendationViewHolder holder,
                                         int position, Recommendation model) {
                holder.bindRecommendation(model);
            }

            @NonNull
            @Override
            public FirebaseRecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_drag, parent, false);
                return new FirebaseRecommendationViewHolder(view);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mFirebaseAdapter.notifyDataSetChanged();
            }
        });
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void dataChanged() {
        mFirebaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }
}
