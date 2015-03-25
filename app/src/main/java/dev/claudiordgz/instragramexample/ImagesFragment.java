package dev.claudiordgz.instragramexample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Claudio on 3/25/2015.
 */
public class ImagesFragment extends Fragment {

    static ImageView fullView, bigImage, firstSmallImage, secondSmallImage;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 12;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    String nextMaxTagId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();

        fullView = (ImageView)activity.findViewById(R.id.fullImage);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(fullView.getLayoutParams());
        lp.setMargins(0, OrientationHelper.getScreenOrientationAndHeight(getActivity().getBaseContext()).second/2, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(lp);
        fullView.setLayoutParams(layoutParams);

        bigImage = (ImageView)activity.findViewById(R.id.bigImage);
        firstSmallImage = (ImageView)activity.findViewById(R.id.firstSmallImage);
        secondSmallImage = (ImageView)activity.findViewById(R.id.secondSmallImage);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView = (RecyclerView)activity.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewAdapter(activity.getApplicationContext(), fullView);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    new LoadImages(activity.getApplicationContext()).execute("60",nextMaxTagId);
                    loading = true;
                }
            }
        });
        new LoadImages(activity.getApplicationContext()).execute("60");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);
        return v;
    }

    private class LoadImages extends AsyncTask<String, Void, Pair<String, ArrayList<String>>> {

        Context context;
        public LoadImages(Context context) {
            this.context = context;
        }

        @Override
        protected Pair<String, ArrayList<String>> doInBackground(String... params) {
            SelfiesRequest request = new SelfiesRequest(context);
            Pair<String, ArrayList<String>> results;
            if(params.length >= 2) {
                results = request.GetImages(null, params[1]);
            } else {
                results = request.GetImages(null, null);
            }
            return results;
        }

        @Override
        protected void onPostExecute(Pair<String, ArrayList<String>> result) {
            nextMaxTagId = result.first;
            int idx = 0;
            for(int i = 0; i <= result.second.size()-3; i+=3) {
                mAdapter.addItem(idx, new ViewHolderModel(result.second.get(i), result.second.get(i + 1), result.second.get(i + 2)));
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
