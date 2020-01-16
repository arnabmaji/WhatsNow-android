package io.github.arnabmaji19.whatsnow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    private final static String SOURCE_URL = "https://github.com/arnabmaji19/WhatsNow-android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView sourceTextView = findViewById(R.id.sourceTextView);
        String html = "<u>This is an Open Source Project<br>Available on Github</u>"; //Underline the Text
        Spanned htmlSpanned = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
        sourceTextView.setText(htmlSpanned);
    }

    public void navigateToSource(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SOURCE_URL)); //Open link in Browser
        startActivity(browserIntent);
    }
}
