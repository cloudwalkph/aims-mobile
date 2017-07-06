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

import com.cloudwalkdigital.aims.App;
import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.data.model.JobOrder;
import com.cloudwalkdigital.aims.userselection.UserSelectionActivity;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobOrderValidateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobOrderValidateFragment extends Fragment {
    @Inject Gson gson;
    @Inject Realm realm;

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

    private JobOrder jobOrder;


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
    public static JobOrderValidateFragment newInstance(Integer jobOrder) {
        JobOrderValidateFragment fragment = new JobOrderValidateFragment();
        Bundle args = new Bundle();
        args.putInt("jobOrder", jobOrder);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getActivity().getApplication()).getNetComponent().inject(this);

        if (getArguments() != null) {
            Integer jo = getArguments().getInt("jobOrder", 0);
            jobOrder = getJobOrder(jo);
        }
    }

    private JobOrder getJobOrder(Integer joId) {
        realm.beginTransaction();
            RealmQuery<JobOrder> query = realm.where(JobOrder.class);
            query.equalTo("id", joId);
            JobOrder jo = query.findFirst();
        realm.commitTransaction();

        return jo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job_order_validate, container, false);

        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);

        if (jobOrder.getPreEvent().equalsIgnoreCase("inactive")) {
            setLocked(ivPreEvent);
        }

        if (jobOrder.getEventProper().equalsIgnoreCase("inactive")) {
            setLocked(ivEventProper);
        }

        if (jobOrder.getPostEvent().equalsIgnoreCase("inactive")) {
            setLocked(ivPostEvent);
        }

        return view;
    }

    @OnClick(R.id.ivPreEvent)
    public void preEvent() {
        moveToSelectRatee("pre");
    }

    @OnClick(R.id.ivEventProper)
    public void eventProper() {
        moveToSelectRatee("event-proper");
    }

    @OnClick(R.id.ivPostEvent)
    public void postEvent() {
        moveToSelectRatee("post");
    }

    private void moveToSelectRatee(String validateType) {
        Intent intent = new Intent(getContext(), UserSelectionActivity.class);
        intent.putExtra("validateType", validateType);
        intent.putExtra("jobOrderId", jobOrder.getId());

        startActivity(intent);
    }

    public static void setLocked(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(128);
        v.setEnabled(false);
    }

    // reset grayscale to colored
    public static void setUnlocked(ImageView v)
    {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }

}
