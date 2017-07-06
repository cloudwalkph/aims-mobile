package com.cloudwalkdigital.aims.projectselection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudwalkdigital.aims.App;
import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.data.APIService;
import com.cloudwalkdigital.aims.data.model.JobOrder;
import com.cloudwalkdigital.aims.data.model.User;
import com.cloudwalkdigital.aims.joborder.JobOrderActivity;
import com.cloudwalkdigital.aims.utils.SessionManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProjectSelectionActivity extends AppCompatActivity {
    @Inject Retrofit retrofit;
    @Inject SharedPreferences sharedPreferences;
    @Inject SessionManager sessionManager;
    @Inject Gson gson;
    @Inject Realm realm;

    @BindView(R.id.rvProjects) RecyclerView mRecyclerViewEvents;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeRefreshLayout;

    private List<JobOrder> projects;
    private DrawerLayout mDrawerLayout;
    public ProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_selection);

        // Bindings
        ((App) getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);

        setupToolbar();
        setupDrawer();

        GetJobOrdersTask getJobOrdersTask = new GetJobOrdersTask();
        getJobOrdersTask.execute();

        mRecyclerViewEvents.setHasFixedSize(true);

        projects = new ArrayList<JobOrder>();
//        for (int i = 1; i <= 5; ++i) {
//            projects.add(new JobOrder("Project "+i, "June 0"+ i, "58BD0B7D68C5"+ i)); //test data
//        }

        // Create adapter passing in the sample user data
        adapter = new ProjectAdapter(ProjectSelectionActivity.this, projects);
        // Attach the adapter to the recyclerview to populate items
        mRecyclerViewEvents.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProjectSelectionActivity.this);
        mRecyclerViewEvents.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewEvents.getContext(),
                layoutManager.getOrientation());

        mRecyclerViewEvents.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetJobOrdersTask getJobOrdersTask = new GetJobOrdersTask();
                getJobOrdersTask.execute();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private List<JobOrder> getJobOrders() {
        APIService service = retrofit.create(APIService.class);
        User user = sessionManager.getUserInformation();

        Call<List<JobOrder>> call = service.getJobOrders(user.getApiToken());

        try {
            Response<List<JobOrder>> response = call.execute();

            if (! response.isSuccessful()) {
                Log.e("PROJECTSELECTION", String.valueOf(response.code()));

                return null;
            }
            Log.i("PROJECTSELECTION", response.body().toString());
            Log.i("PROJECTSELECTION", user.getApiToken());

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Assigned Job Order");
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_logout:
                                // logout currently logged in user
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Project Adapter
     */
    public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

        private List<JobOrder> mProject;

        private Context mContext;

        public ProjectAdapter(Context mContext, List<JobOrder> mProject) {
            this.mProject = mProject;
            this.mContext = mContext;
        }

        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.item_project, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // Get the data model based on position
            JobOrder project = mProject.get(position);

            // Set item views based on your views and data model
            TextView textView = holder.nameTextView;
            textView.setText(project.getProjectName());

            TextView deadline = holder.deadlineTextView;
            deadline.setText(project.getStartDate() == null || project.getStartDate().isEmpty() ? "No Schedule Set" : project.getStartDate());

            TextView jobOrderNo = holder.joTextView;
            jobOrderNo.setText(project.getJobOrderNo());
        }

        @Override
        public int getItemCount() {
            if (mProject == null) {
                return 0;
            }

            return mProject.size();
        }

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView nameTextView;
            public TextView deadlineTextView;
            public TextView joTextView;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                nameTextView = (TextView) itemView.findViewById(R.id.project_name);
                deadlineTextView = (TextView) itemView.findViewById(R.id.deadline);
                joTextView = (TextView) itemView.findViewById(R.id.job_order_no);

                itemView.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    JobOrder project = mProject.get(position);

                    // We can access the data within the views
                    Toast.makeText(getContext(), project.getProjectName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProjectSelectionActivity.this, JobOrderActivity.class);

                    String jo = gson.toJson(project, JobOrder.class);
                    intent.putExtra("jobOrder", jo);

                    startActivity(intent);
                }
            }
        }
    }

    public class GetJobOrdersTask extends AsyncTask<String, Void, List<JobOrder>> {
        @Override
        protected List<JobOrder> doInBackground(String... params) {
            return getJobOrders();
        }

        @Override
        protected void onPostExecute(List<JobOrder> jo) {
            super.onPostExecute(jo);

            realm.beginTransaction();
                if (jo == null) {
                    RealmQuery<JobOrder>  joQuery = realm.where(JobOrder.class);
                    RealmResults<JobOrder> localJobOrders = joQuery.findAll();
                    projects = localJobOrders;
                } else {
                    projects = realm.copyToRealmOrUpdate(jo);
                }
            realm.commitTransaction();

//          projects = jo;
            mRecyclerViewEvents.setAdapter(new ProjectAdapter(ProjectSelectionActivity.this, projects));
            mRecyclerViewEvents.invalidate();

            swipeRefreshLayout.setRefreshing(false);
        }
    }
}