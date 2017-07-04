package com.cloudwalkdigital.aims.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudwalkdigital.aims.R;

public class QuestionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragmentTitle);
        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static QuestionFragment newInstance(String title) {

        QuestionFragment f = new QuestionFragment();
        Bundle b = new Bundle();
        b.putString("msg", title);

        f.setArguments(b);

        return f;
    }
}
