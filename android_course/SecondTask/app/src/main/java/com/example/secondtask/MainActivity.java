package com.example.secondtask;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Fragment defaultFragment;
    private Fragment settingsFragment;
    private Fragment searchFragment;
    private FragmentManager fragmentManager;

    {
        defaultFragment = new DefaultFragment();
        settingsFragment = new SettingsFragment();
        searchFragment = new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frgmCont, defaultFragment).addToBackStack(null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSettings:
                Toast.makeText(this, R.string.menu_settings, Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.frgmCont, settingsFragment).addToBackStack(null).commit();
                return true;
            case R.id.actionSearch:
                Toast.makeText(this, R.string.menu_search, Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.frgmCont, searchFragment).addToBackStack(null).commit();
                return true;
            case R.id.actionExit:
                Toast.makeText(this, R.string.menu_exit, Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 1) finish();
        else fragmentManager.popBackStack();
    }
}