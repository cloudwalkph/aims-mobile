package com.cloudwalkdigital.aims;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.cloudwalkdigital.aims.projectselection.ProjectSelectionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_log_in) Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_log_in)
    public void doLogin(Button button) {
        Intent intent = new Intent(LoginActivity.this, ProjectSelectionActivity.class);
        startActivity(intent);
    }
}
