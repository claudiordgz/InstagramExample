package dev.claudiordgz.android.testing_app;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import dev.claudiordgz.android.sliderlib.fragments.SlidingTabsViewPagerFragment;
import dev.claudiordgz.android.sliderlib.views.adapters.TraineeCardAdapter;
import dev.claudiordgz.android.sliderlib.views.custom_views.models.ListOfTabs;
import dev.claudiordgz.android.sliderlib.views.custom_views.models.SingleTab;
import dev.claudiordgz.android.sliderlib.views.listeners.ColorChangeListener;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    ListOfTabs tabs = setupTabs();

    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    TraineeCardAdapter adapter = new TraineeCardAdapter(getSupportFragmentManager());
    adapter.setTabs(tabs.getTabs());
    viewPager.setAdapter(adapter);

    ColorChangeListener mColorListener = new ColorChangeListener(this);
    mColorListener.setViewPager(viewPager);
    mColorListener.setActionBar(getSupportActionBar());

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    SlidingTabsViewPagerFragment fragment = new SlidingTabsViewPagerFragment();
    fragment.setColorListener(mColorListener);
    fragment.setViewPager(viewPager);

    Bundle bundle = new Bundle();
    bundle.putParcelable(SlidingTabsViewPagerFragment.TAB_ARGUMENTS, tabs);
    fragment.setArguments(bundle);
    transaction.replace(R.id.sample_content_fragment, fragment);
    transaction.commit();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private ListOfTabs setupTabs() {
    return new ListOfTabs(
        new SingleTab(
            "First Tab", // Title
            getResources().getColor(R.color.indigo_500), // Indicator color
            getResources().getColor(R.color.blue_grey_500), // Divider color
            getResources().getColor(R.color.indigo_100), // Background color
            getResources().getColor(R.color.indigo_700)
        ),
        new SingleTab(
            "Second Tab", // Title
            getResources().getColor(R.color.green_500), // Indicator color
            getResources().getColor(R.color.blue_grey_500), // Divider color
            getResources().getColor(R.color.green_100), // Background color
            getResources().getColor(R.color.green_700)
        ),
        new SingleTab(
            "Third Tab", // Title
            getResources().getColor(R.color.orange_500), // Indicator color
            getResources().getColor(R.color.blue_grey_500), // Divider color
            getResources().getColor(R.color.orange_100), // Background color
            getResources().getColor(R.color.orange_700)
        )
    );
  }
}
