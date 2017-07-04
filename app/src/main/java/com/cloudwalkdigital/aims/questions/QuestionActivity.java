package com.cloudwalkdigital.aims.questions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloudwalkdigital.aims.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends AppCompatActivity {

    public ViewPager mPager;

    @BindView(R.id.progressBar) ProgressBar pBar;
    @BindView(R.id.vpQuestions) ViewPager vpQuestion;
    @BindView(R.id.question_max) TextView mMaximumQuestion;
    @BindView(R.id.question_min) TextView mMinimumQuestion;
    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.bind(this);

        setupToolbar();
        pBar.setProgress(0);

        final ViewPager mPager = (ViewPager) findViewById(R.id.vpQuestions);
        mPager.setAdapter(new QuestionsAdapter(getSupportFragmentManager()));

        mMaximumQuestion.setText(String.valueOf(mPager.getAdapter().getCount()));

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                int curPosition = mPager.getCurrentItem() + 1;
                int totalItems = mPager.getAdapter().getCount();
                float progress = (float) curPosition / totalItems;
                float pTotal = progress * 100;
                pBar.setProgress((int) pTotal);
                mMinimumQuestion.setText(String.valueOf(curPosition));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Pre Event");
        ab.setDisplayHomeAsUpEnabled(true);
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

    @OnClick(R.id.nxtBtn)
    public void nextPage(){
        int totalPage = vpQuestion.getAdapter().getCount();
        int page = getItem() + 1;
        if(page < totalPage){
            mMinimumQuestion.setText(String.valueOf(page+1));
            vpQuestion.setCurrentItem(page, true);
        }
    }

    @OnClick(R.id.prevBtn)
    public void prevPage(){
        int page = getItem() - 1;
        if(page >= 0){
            mMinimumQuestion.setText(String.valueOf(page+1));
            vpQuestion.setCurrentItem(page, true);
        }
    }
    private int getItem() {
        return vpQuestion.getCurrentItem();
    }

}
