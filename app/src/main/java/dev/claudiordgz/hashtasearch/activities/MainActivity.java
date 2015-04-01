package dev.claudiordgz.hashtasearch.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import dev.claudiordgz.hashtasearch.R;
import dev.claudiordgz.hashtasearch.fragments.RecyclerFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.instagram_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = new RecyclerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(RecyclerFragment.argHashTag, "selfie");
            fragment.setArguments(bundle);
            manager.beginTransaction().add(R.id.container, fragment).commit();
        }
    }
}
