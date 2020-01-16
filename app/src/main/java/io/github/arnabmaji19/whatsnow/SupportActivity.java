package io.github.arnabmaji19.whatsnow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SupportActivity extends AppCompatActivity {

    private final static String LINKEDIN_PROFILE_LINK = "https://www.linkedin.com/in/arnab-maji-8a31b3192/";
    private final static String GITHUB_PROFILE_LINK = "https://github.com/arnabmaji19";
    private final static String INSTAGRAM_PROFILE_LINK = "https://www.instagram.com/arnabmaji19/";
    private final static String PAYPAL_PROFILE_LINK = "https://www.paypal.me/arnabmaji19";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        Toolbar toolbar = findViewById(R.id.support_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void viewProfile(View view) {
        int tag = Integer.parseInt(view.getTag().toString());

        String url = "";
        switch (tag) { //Determine url based upon choice
            case 1:
                url = LINKEDIN_PROFILE_LINK;
                break;
            case 2:
                url = GITHUB_PROFILE_LINK;
                break;
            case 3:
                url = INSTAGRAM_PROFILE_LINK;
                break;
        }

        //Open url in browser
        openInBrowser(url);
    }

    public void viewPaypalAccount(View view) {
        openInBrowser(PAYPAL_PROFILE_LINK);
    }

    private void openInBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
