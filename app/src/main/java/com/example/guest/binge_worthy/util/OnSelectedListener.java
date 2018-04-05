package com.example.guest.binge_worthy.util;


import com.example.guest.binge_worthy.models.Recommendation;

import java.util.ArrayList;

public interface OnSelectedListener {
    public void onItemSelected(Integer position, ArrayList<Recommendation> recommendations, String source);
}
