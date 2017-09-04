# Easy Android GitHub OAuth #

### About ###

A simple client for adding OAuth authentication with the GitHub API to your Android app.

_MINIMUM API 19 (Ice Cream Sandwich)_

---

### Usage ###

Make sure you specify JCenter module repository in project level build.gradle.

    allprojects {
        repositories {
            jcenter()
        }
    }

Define compilation dependency in app level build.gradle.

    compile 'com.andrewnitu:easygithuboauth:1.0.0'

Launch the GitHub authorization activity. clientId and secret can be obtained through GitHub developer settings. 

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