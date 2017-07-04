package com.cloudwalkdigital.aims.questions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cloudwalkdigital.aims.R;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ViewPager pager = (ViewPager) findViewById(R.id.vpQuestions);
        pager.setAdapter(new QuestionsAdapter(getSupportFragmentManager()));
    }

    private class QuestionsAdapter extends FragmentPagerAdapter {

        public QuestionsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return QuestionFragment.newInstance("FirstFragment, Instance 1");
                case 1: return QuestionFragment.newInstance("SecondFragment, Instance 1");
                case 2: return QuestionFragment.newInstance("ThirdFragment, Instance 1");
                case 3: return QuestionFragment.newInstance("ThirdFragment, Instance 2");
                case 4: return QuestionFragment.newInstance("ThirdFragment, Instance 3");
                default: return QuestionFragment.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
