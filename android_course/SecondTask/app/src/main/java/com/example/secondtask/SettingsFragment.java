package com.example.secondtask;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsFragment extends Fragment {

    private RadioGroup radioGroup;
    public static final String SECOND_TASK_PREFS = "SETTINGS";
    final String KEY_RADIOBUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    final String SEARCH_SYSTEM = "SEARCH";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, null);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        radioGroup = getActivity().findViewById(R.id.settings_checker);

        PrefsHelper prefsHelperForLoad = new PrefsHelper(getContext());
        int savedRadioIndex = prefsHelperForLoad.loadButtonIndex();
        RadioButton savedCheckedRadioButton = (RadioButton) radioGroup.getChildAt(savedRadioIndex);
        savedCheckedRadioButton.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = radioGroup.findViewById(checkedId);
                int checkedIndex = radioGroup.indexOfChild(checkedRadioButton);
                PrefsHelper prefsHelperForSave = new PrefsHelper(getContext());
                switch (checkedId) {
                    case R.id.google_radio:
                        prefsHelperForSave.savePrefs(checkedIndex, "https://www.google.ru/#q=");
                        break;
                    case R.id.yandex_radio:
                        prefsHelperForSave.savePrefs(checkedIndex, "https://yandex.ru/?text=");
                        break;
                    case R.id.bing_radio:
                        prefsHelperForSave.savePrefs(checkedIndex, "https://www.bing.com/search?q=");
                        break;
                }
            }
        });
    }
}