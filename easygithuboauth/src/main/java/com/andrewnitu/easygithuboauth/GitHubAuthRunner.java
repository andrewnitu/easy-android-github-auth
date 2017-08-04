package com.andrewnitu.easygithuboauth;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Andrew Nitu on 8/3/2017.
 */

public class GitHubAuthRunner {
    private String id;
    private String secret;
    private Context context;

    public GitHubAuthRunner(String id, String secret, Context context) {
        this.id = id;
        this.secret = secret;
        this.context = context;
    }

    public void start() {
        Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("secret", secret);
        context.startActivity(intent);
    }
}