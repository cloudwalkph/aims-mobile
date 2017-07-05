package com.cloudwalkdigital.aims.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.data.model.Choice;
import com.cloudwalkdigital.aims.data.model.Question;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {
    public static List<Choice> mChoices;
    public LinearLayout ll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        TextView tv = (TextView) v.findViewById(R.id.question_details);
        ll = (LinearLayout)v.findViewById(R.id.linear_layout_answer);
        tv.setText(getArguments().getString("msg"));

        Gson gson = new Gson();
        String json = getArguments().getString("question");
        Question question = gson.fromJson(json, Question.class);
        mChoices = question.getChoices();
        initChoices();


        return v;
    }

    public static QuestionFragment newInstance(Question question) {

        QuestionFragment f = new QuestionFragment();
        Bundle b = new Bundle();
        b.putString("msg", question.getQuestion());

        Gson gson = new Gson();
        String json = gson.toJson(question);
        b.putString("question", json);
        f.setArguments(b);

        return f;
    }

    public void initChoices() {
        RadioGroup mRadioGroup = new RadioGroup(this.getContext());
        mRadioGroup.setOrientation(RadioGroup.VERTICAL);

        mRadioGroup.setLayoutParams(ll.getLayoutParams());

        for (Choice choice : mChoices) {
            RadioButton rb = new RadioButton(this.getContext());

            rb.setText(choice.getChoice());
            rb.setId((int) choice.getId());
            rb.setTextSize(14);
            rb.setPadding(5, 10, 5, 10);
            mRadioGroup.addView(rb);
        }
        ll.addView(mRadioGroup);

    }
}
