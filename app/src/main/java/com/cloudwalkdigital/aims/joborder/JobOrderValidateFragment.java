package com.cloudwalkdigital.aims.joborder;


import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.userselection.UserSelectionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobOrderValidateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobOrderValidateFragment extends Fragment {

    @BindView(R.id.ivPreEvent) ImageView ivPreEvent;
    @BindView(R.id.ivEventProper) ImageView ivEventProper;
    @BindView(R.id.ivPostEvent) ImageView ivPostEvent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public JobOrderValidateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JobOrderValidateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobOrderValidateFragment newInstance() {
        JobOrderValidateFragment fragment = new JobOrderValidateFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job_order_validate, container, false);

        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.ivPreEvent)
    public void preEvent() {
        Intent intent = new Intent(getContext(), UserSelectionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ivEventProper)
    public void eventProper() {

        Intent intent = new Intent(getContext(), UserSelectionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ivPostEvent)
    public void postEvent() {

    }

}
