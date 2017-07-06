package com.cloudwalkdigital.aims;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cloudwalkdigital.aims.data.APIService;
import com.cloudwalkdigital.aims.data.model.User;
import com.cloudwalkdigital.aims.projectselection.ProjectSelectionActivity;
import com.cloudwalkdigital.aims.utils.SessionManager;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    @Inject Retrofit retrofit;
    @Inject SharedPreferences sharedPreferences;
    @Inject SessionManager sessionManager;

    @BindView(R.id.btn_log_in) Button mBtnLogin;
    @BindView(R.id.etEmailAddress) EditText mEmail;
    @BindView(R.id.etPassword) EditText mPassword;
    @BindView(R.id.login_parent_view) LinearLayout parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bindings
        ((App) getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);

        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, ProjectSelectionActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_log_in)
    public void doLogin(Button button) {
        button.setText(R.string.btn_logging_in);
        button.setEnabled(false);

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        AuthTask authTask = new AuthTask();
        authTask.execute(email, password);
    }

    private void showFailedSnackbar() {
        Snackbar snackbar = Snackbar.make(parentView, R.string.failed_login, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sbView.getLayoutParams();
        params.gravity = Gravity.TOP;
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.red_400));

        snackbar.show();
    }

    private void resetButton() {
        mBtnLogin.setText(R.string.btn_login);
        mBtnLogin.setEnabled(true);
    }

    private Boolean attemptAuth(String email, String password) {
        APIService service = retrofit.create(APIService.class);

        Call<User> call = service.authenticate(email, password);

        try {
            Response<User> response = call.execute();
            Log.i("LOGINACTIVITY", String.valueOf(response.code()));
            if (! response.isSuccessful()) {
                return false;
            }

            User user = response.body();

            // Save user information
            sessionManager.setUserInformation(user);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public class AuthTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Boolean result = attemptAuth(params[0], params[1]);

            return result;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);

            if (! s) {
                resetButton();
                showFailedSnackbar();

                return;
            }

            // Move into the next activity
            Intent intent = new Intent(LoginActivity.this, ProjectSelectionActivity.class);
            startActivity(intent);
        }
    }
}
