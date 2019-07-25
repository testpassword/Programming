package com.example.secondtask;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class SearchFragment extends Fragment {

    private Button searchButton;
    private EditText phraseEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchButton = getActivity().findViewById(R.id.start_searching);
        phraseEditText = getActivity().findViewById(R.id.write_searching);
        final PrefsHelper prefsHelperForLoad = new PrefsHelper(getContext());
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phraseEditText.getText())) {
                    Intent startBrowserIntent = new Intent(Intent.ACTION_VIEW);
                    PackageManager packageManager = getActivity().getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(startBrowserIntent, 0);
                    startBrowserIntent.setData(Uri.parse(prefsHelperForLoad.loadSearcher() + phraseEditText.getText()));
                    if (activities.size() > 0) getActivity().startActivity(startBrowserIntent);
                } else Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}