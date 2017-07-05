package com.cloudwalkdigital.aims.questions;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloudwalkdigital.aims.App;
import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.data.APIService;
import com.cloudwalkdigital.aims.data.model.Question;
import com.cloudwalkdigital.aims.data.model.User;
import com.cloudwalkdigital.aims.userselection.UserSelectionActivity;
import com.cloudwalkdigital.aims.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuestionActivity extends AppCompatActivity {
    @Inject Retrofit retrofit;
    @Inject SharedPreferences sharedPreferences;
    @Inject SessionManager sessionManager;

    @BindView(R.id.progressBar) ProgressBar pBar;
    @BindView(R.id.question_max) TextView mMaximumQuestion;
    @BindView(R.id.question_min) TextView mMinimumQuestion;
    @BindView(R.id.vpQuestions) ViewPager mPager;
    int currentPage;

    public String validateType;
    public Integer jobOrderId;
    public Integer rateeId;
    public List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ((App) getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);

        validateType = getIntent().getStringExtra("validateType");
        jobOrderId = getIntent().getIntExtra("jobOrderId", 0);
        rateeId = getIntent().getIntExtra("rateeId", 0);

        getQuestions();

        setupToolbar();
        pBar.setProgress(1);
    }

    private void getQuestions() {
        APIService service = retrofit.create(APIService.class);
        User user = sessionManager.getUserInformation();

        Call<List<Question>> call = service.getQuestions(rateeId, jobOrderId, validateType, user.getApiToken());
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                Log.i("QUESTIONACTIVITY", response.toString());
                questions = response.body();

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

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {

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
//            switch(pos) {
//
//                case 0: return QuestionFragment.newInstance("FirstFragment, Instance 1");
//                case 1: return QuestionFragment.newInstance("SecondFragment, Instance 1");
//                case 2: return QuestionFragment.newInstance("ThirdFragment, Instance 1");
//                case 3: return QuestionFragment.newInstance("ThirdFragment, Instance 2");
//                case 4: return QuestionFragment.newInstance("ThirdFragment, Instance 3");
//                default: return QuestionFragment.newInstance("ThirdFragment, Default");
//            }

            Question question = questions.get(pos);

            return QuestionFragment.newInstance(question);
        }

        @Override
        public int getCount() {
            return questions.size();
        }
    }

    @OnClick(R.id.nxtBtn)
    public void nextPage(){
        int totalPage = mPager.getAdapter().getCount();
        int page = getItem() + 1;
        if(page < totalPage){
            mMinimumQuestion.setText(String.valueOf(page+1));
            mPager.setCurrentItem(page, true);
        }
    }

    @OnClick(R.id.prevBtn)
    public void prevPage(){
        int page = getItem() - 1;
        if(page >= 0){
            mMinimumQuestion.setText(String.valueOf(page+1));
            mPager.setCurrentItem(page, true);
        }
    }
    private int getItem() {
        return mPager.getCurrentItem();
    }

}
