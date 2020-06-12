package com.innofied.innofiedassignment.UI.Launcher;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.innofied.innofiedassignment.R;
import com.innofied.innofiedassignment.UI.UserList.UserListActivity;

public class SplashActivity extends AppCompatActivity implements TempInterface {

    private static final int SPLASH_TIME = 2000;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        context = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, UserListActivity.class));
                finish();
            }
        }, SPLASH_TIME);

    }

    @Override
    public void a() {

    }

    @Override
    public void b() {

    }

    @Override
    public void c() {

    }

    @Override
    public void d() {

    }
}
