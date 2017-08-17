package com.andrewnitu.easygithuboauth;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Andrew Nitu on 8/3/2017.
 */

public class AuthActivity extends AppCompatActivity {
    private final String DEBUG_TAG = "easygithuboauth";

    private final String GITHUB_OAUTH_CODE = "https://github.com/login/oauth/authorize";
    private final String GITHUB_OAUTH_TOKEN = "https://github.com/login/oauth/access_token";

    private String id;
    private String secret;

    private WebView webView;

    private RequestQueue requestQueue;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_githuboauth);

        Intent receivedIntent = getIntent();

        if (hasNecessaryExtras(receivedIntent)) {
            getExtras(receivedIntent);
        }
        else {
            Log.e(DEBUG_TAG, "An error occurred getting the intent extras");
            finish();
        }

        webView = (WebView) findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (!url.contains("?code=")) {
                    return false;
                }

                return false;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                String url = request.getUrl().toString();

                if (!url.contains("?code=")) {
                    return false;
                }

                String code = url.substring(url.lastIndexOf("?code=") + 1);

                getToken((code.split("=")[1]).split("&")[1]);

                return false;
            }
        });
    }

    private void getToken(String code) {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GITHUB_OAUTH_TOKEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String token = jsonObject.getString("access_token");

                            storeTokenToSharedPreferences(token);
                        }
                        catch(JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("client_id", id);
                params.put("client_secret", secret);
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        requestQueue.add(stringRequest);
    }

    private void storeTokenToSharedPreferences(String token) {
        SharedPreferences sp = getSharedPreferences("github-token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    private boolean hasNecessaryExtras(Intent intent) {
        return intent.hasExtra("id") && intent.hasExtra("secret");
    }

    private void getExtras(Intent intent) {
        id = intent.getStringExtra("id");
        secret = intent.getStringExtra("secret");
    }
}
