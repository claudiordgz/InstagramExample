package dev.claudiordgz.hashtasearch.data_providers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

import dev.claudiordgz.common.model.InstagramData;
import dev.claudiordgz.hashtasearch.model.TripletImages;
import dev.claudiordgz.hashtasearch.model.TripletImagesManager;
import dev.claudiordgz.hashtasearch.data_providers.network.SelfiesRequest;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

/**
 * Created by Claudio on 3/26/2015.
 */
public class BackgroundImageTask extends AsyncTask<String, Void, Pair<String, ArrayList<InstagramData>>> {
    private Context context;
    private EasyRecyclerAdapter<TripletImages> mAdapter;
    private String nextImage;
    private LinkedHashSet<InstagramData> urlCache;
    private TripletImagesManager mImageManager;
    private String mTag;

    public BackgroundImageTask(Context context, EasyRecyclerAdapter<TripletImages> adapter,
                               LinkedHashSet<InstagramData> urlCache, TripletImagesManager imagesManager,
                               String nextImage, String tag) {
        this.context = context;
        this.mAdapter = adapter;
        this.urlCache = urlCache;
        this.mImageManager = imagesManager;
        this.nextImage = nextImage;
        this.mTag = tag;
    }

    @Override
    protected Pair<String, ArrayList<InstagramData>> doInBackground(String... params) {
        SelfiesRequest request = new SelfiesRequest(context);
        return request.GetImages(mTag, nextImage);
    }

    @Override
    protected void onPostExecute(Pair<String, ArrayList<InstagramData>> result) {
        nextImage = result.first;
        if(nextImage == null) return;
        int lastPosition = urlCache.size() == 0 ? 0 : urlCache.size();
        boolean newItemsAdded = false;
        for(InstagramData data : result.second) {
            urlCache.add(data);
        }
        Iterator<InstagramData> it = urlCache.iterator();
        for(int i = 0; i != lastPosition; ++i) {
            it.next();
        }
        while (it.hasNext()) {
            mImageManager.add(it.next());
            newItemsAdded = true;
        }
        if(newItemsAdded) {
            mAdapter.addItems(mImageManager.getImageList());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}