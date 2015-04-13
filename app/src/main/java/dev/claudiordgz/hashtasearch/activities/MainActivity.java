package dev.claudiordgz.hashtasearch.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import dev.claudiordgz.common.model.PropertiesUtils;
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
      boolean isTablet = PropertiesUtils.isTabletDevice(this);
      fragment = RecyclerFragment.newInstance(0, "selfie", isTablet);
      manager.beginTransaction().add(R.id.container, fragment).commit();
    }
  }
}
