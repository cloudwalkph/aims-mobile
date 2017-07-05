package com.cloudwalkdigital.aims.questions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.projectselection.ProjectSelectionActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_complete);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_home)
    public void onClick() {
        Intent intent = new Intent(QuestionCompleteActivity.this, ProjectSelectionActivity.class);
        startActivity(intent);
        finish();
    }
}
