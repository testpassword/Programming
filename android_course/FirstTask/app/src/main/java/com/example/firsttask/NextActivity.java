package com.example.firsttask;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import java.util.List;

public class NextActivity extends AppCompatActivity {

    public static String BIG_TEXT;
    private TextView textViewer;
    private Button badder;
    private View.OnClickListener OnBadderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri webPageAddress = Uri.parse("https://www.google.ru/#q=" + textViewer.getText());
            Intent startBrowserIntent = new Intent(Intent.ACTION_VIEW, webPageAddress);
            PackageManager packageManager = getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(startBrowserIntent, 0);
            if (activities.size() > 0) startActivity(startBrowserIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_next);
        textViewer = findViewById(R.id.text_viewer);
        badder = findViewById(R.id.badder);
        Bundle bundle = getIntent().getExtras();
        textViewer.setText(bundle.getString(BIG_TEXT));
        badder.setOnClickListener(OnBadderClickListener);
    }
}
