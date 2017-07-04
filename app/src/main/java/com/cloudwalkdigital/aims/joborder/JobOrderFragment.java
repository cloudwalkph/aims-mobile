package com.cloudwalkdigital.aims.joborder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudwalkdigital.aims.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// In this case, the fragment displays simple text based on the page
public class JobOrderFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static JobOrderFragment newInstance() {
        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
        JobOrderFragment fragment = new JobOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_order, container, false);
        TextView textView = (TextView) view;
        textView.setText("Fragment #" + mPage);
        return view;
    }
}
