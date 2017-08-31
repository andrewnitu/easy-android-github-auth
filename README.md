# Easy Android GitHub OAuth #

### About ###

A simple client for adding OAuth authentication with the GitHub API to your Android app.

_MINIMUM API 19 (Ice Cream Sandwich)_

---

### Usage ###

Launch the GitHub authorization activity like so.

clientId and secret can be obtained through GitHub developer settings.
context is your current context, so likely 'this' for activities and 'getActivity()' for fragments.

    GitHubAuthRunner gitHubAuthRunner = new GitHubAuthRunner(clientId, secret, context);
    gitHubAuthRunner.start();

This will launch a WebView where the user can login and authorize your app.

Once the token is obtained it will be stored in shared preferences "github-token" with tag "token".

Recommend checking if token exists already, otherwise launch the login activity.

    SharedPreferences sp = getSharedPreferences("github-token", MODE_PRIVATE);
    if (!sp.contains("token")) {
        GitHubAuthRunner gitHubAuthRunner = new GitHubAuthRunner(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, this);
        gitHubAuthRunner.start();
    }
    else {
        Log.d("AndroidOAuth", "token already stored in sharedpreferences, auth dialog not shown");
    }

See example app for more details.

---

### Contributing ###

Feel free to submit pull requests.