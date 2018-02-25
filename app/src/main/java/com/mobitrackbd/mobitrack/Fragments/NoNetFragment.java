package com.mobitrackbd.mobitrack.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitrackbd.mobitrack.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoNetFragment extends Fragment {


    public NoNetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_net, container, false);
    }

}
