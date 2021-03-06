package com.example.guest.binge_worthy.adapters;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.util.ItemTouchHelperAdapter;
import com.example.guest.binge_worthy.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.ChildEventListener;
import java.util.ArrayList;

public class MyFireBaseRecyclerAdapter extends FirebaseRecyclerAdapter<Recommendation, FirebaseRecommendationViewHolder> implements ItemTouchHelperAdapter {
    private OnStartDragListener mOnStartDragListener;
    private ChildEventListener mChildEventListener;
    private ArrayList<Recommendation> mRecommendatons = new ArrayList<>();
    private ObservableSnapshotArray<Recommendation> snapshotArray;

    public MyFireBaseRecyclerAdapter(FirebaseRecyclerOptions options) {
        super(options);

    }

    @Override
    public FirebaseRecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_drag, parent);
        return new FirebaseRecommendationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final FirebaseRecommendationViewHolder holder, int Position, Recommendation model) {
        holder.bindRecommendation(model);
        holder.mIconImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        getRef(position).removeValue();

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }





}
