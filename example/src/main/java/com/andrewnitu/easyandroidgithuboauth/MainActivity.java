package com.andrewnitu.easyandroidgithuboauth;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.andrewnitu.easygithuboauth.GitHubAuthRunner;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getTokenFromSharedPrefs(View view) {
        SharedPreferences shared = getSharedPreferences("github-token", MODE_PRIVATE);
        String token = (shared.getString("token", ""));

        Log.e("TOKEN", "This is the token retrieved from sharedprefs: " + token);
    }

    public void getTokenFromGitHubApi(View view) {
        SharedPreferences sp = getSharedPreferences("github-token", MODE_PRIVATE);
        if (!sp.contains("token")) {
            GitHubAuthRunner gitHubAuthRunner = new GitHubAuthRunner(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, this);
            gitHubAuthRunner.start();
        }
        else {
            Log.d("AndroidOAuth", "token already stored in sharedpreferences, auth dialog not shown");
        }
    }
}
