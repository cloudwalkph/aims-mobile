package com.cloudwalkdigital.aims.joborder;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cloudwalkdigital.aims.App;
import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.data.model.JobOrder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class JobOrderActivity extends AppCompatActivity {
    @Inject Gson gson;

    public List<Fragment> fragments;
    private JobOrder jobOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_order);

        ((App) getApplication()).getNetComponent().inject(this);

        // Get attached job order from the intent
        String jo = getIntent().getStringExtra("jobOrder");
        jobOrder = gson.fromJson(jo, JobOrder.class);

        if (jobOrder == null) {
            finish();
        }

        setupToolbar();

        fragments = new ArrayList<Fragment>();
        fragments.add(JobOrderFragment.newInstance());
        fragments.add(JobOrderDiscussionsFragment.newInstance(jobOrder.getDiscussions(), jobOrder.getId()));
        fragments.add(JobOrderValidateFragment.newInstance(jobOrder));

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                JobOrderActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(jobOrder.getProjectName());
        ab.setSubtitle(jobOrder.getJobOrderNo());
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        jobOrder = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[] { "Details", "Discussions", "Validate" };
        private Context context;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
