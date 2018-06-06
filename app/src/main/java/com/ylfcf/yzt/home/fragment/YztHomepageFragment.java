package com.ylfcf.yzt.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ylfcf.yzt.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class YztHomepageFragment extends Fragment {


    public YztHomepageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yzt_homepage, container, false);
    }

}
