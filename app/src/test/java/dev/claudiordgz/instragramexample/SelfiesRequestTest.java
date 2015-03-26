package dev.claudiordgz.instragramexample;

import android.util.Log;
import android.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import dev.claudiordgz.instragramexample.helpers.StyleChangerRobolectricTestRunner;
import dev.claudiordgz.instragramexample.network.SelfiesRequest;

/**
 * Created by Claudio on 3/25/2015.
 */
@Config(manifest = "src/main/AndroidManifest.xml")
@RunWith(StyleChangerRobolectricTestRunner.class)
public class SelfiesRequestTest {

    private String TAG = getClass().getName();

    @Test
    public void testGetImages(){
        SelfiesRequest request = new SelfiesRequest(Robolectric.application.getBaseContext());
        Pair<String, ArrayList<String>> results = request.GetImages(null, null);
        Pair<String, ArrayList<String>> results_next = request.GetImages(null, results.first);

        Log.d(TAG, "Finish the test");
    }
}
