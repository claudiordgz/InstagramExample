package dev.claudiordgz.hashtasearch;

import android.util.Pair;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import dev.claudiordgz.hashtasearch.helpers.StyleChangerRobolectricTestRunner;
import dev.claudiordgz.hashtasearch.data_providers.network.SelfiesRequest;
import dev.claudiordgz.hashtasearch.model.InstagramData;

/**
 * Created by Claudio on 3/25/2015.
 */
@Config(manifest = "src/main/AndroidManifest.xml")
@RunWith(StyleChangerRobolectricTestRunner.class)
public class SelfiesRequestTest {

  private String TAG = getClass().getName();

  @Before
  public void setUp() throws Exception {
    ShadowLog.stream = System.out;

  }

  @Test
  public void testGetImagesDuplicates() {
    doATest(200);
  }

  @Test
  public void testGetImagesMassive() {
    doATest(20000);
  }

  public void doATest(int n) {
    SelfiesRequest request = new SelfiesRequest(Robolectric.application.getBaseContext());
    Pair<String, ArrayList<InstagramData>> results = request.GetImages(null, null);
    Pair<String, ArrayList<InstagramData>> results_next = request.GetImages(null, results.first);
    ArrayList<InstagramData> simple_result_list = results_next.second;
    String next = results_next.first;
    for (int i = 0; i != n; ++i) {
      results = request.GetImages(null, next);
      next = results.first;
      if (next == null) {
        break;
      }
      simple_result_list.addAll(results.second);
    }
    Set<InstagramData> has_duplicates = new HashSet<>(simple_result_list);
    Assert.assertTrue(has_duplicates.size() == simple_result_list.size());
  }

  @Test
  public void testGetImages() {
    doATest(5);
  }


}
