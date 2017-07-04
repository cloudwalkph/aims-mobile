package com.cloudwalkdigital.aims.userselection;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.data.model.User;
import com.cloudwalkdigital.aims.questions.QuestionActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSelectionActivity extends AppCompatActivity {

    @BindView(R.id.rvUsers) RecyclerView mRecyclerViewUsers;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);
        ButterKnife.bind(this);

        setupToolbar();

        mRecyclerViewUsers.setHasFixedSize(true);

        users = new ArrayList<User>();
        users.add(new User("Jane", "Doe", "Inventory")); //test data
        users.add(new User("John", "Doe", "Creatives")); //test data

        // Create adapter passing in the sample user data
        UserSelectionActivity.UserAdapter adapter = new UserSelectionActivity.UserAdapter(UserSelectionActivity.this, users);
        // Attach the adapter to the recyclerview to populate items
        mRecyclerViewUsers.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserSelectionActivity.this);
        mRecyclerViewUsers.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewUsers.getContext(),
                layoutManager.getOrientation());

        mRecyclerViewUsers.addItemDecoration(dividerItemDecoration);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Select User");
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Project Adapter
     */
    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

        private List<User> mUser;

        private Context mContext;

        public UserAdapter(Context mContext, List<User> mUser) {
            this.mUser = mUser;
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
            View contactView = inflater.inflate(R.layout.item_user, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // Get the data model based on position
            User user = mUser.get(position);

            // Set item views based on your views and data model
            TextView textView = holder.nameTextView;
            textView.setText(user.getFirstName()+" "+ user.getLastName());

            TextView department = holder.departmentTextView;
            department.setText(user.getDepartment());
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView nameTextView;
            public TextView departmentTextView;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                nameTextView = (TextView) itemView.findViewById(R.id.user_name);
                departmentTextView = (TextView) itemView.findViewById(R.id.department_name);

                itemView.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    User user = mUser.get(position);

                    // We can access the data within the views
                    Toast.makeText(getContext(), user.getDepartment(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserSelectionActivity.this, QuestionActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}