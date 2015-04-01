package dev.claudiordgz.hashtasearch.data_providers.network;

import android.content.Context;
import android.util.Log;
import android.util.Pair;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import dev.claudiordgz.hashtasearch.R;
import dev.claudiordgz.hashtasearch.model.InstagramData;


/**
 * Created by Claudio on 3/25/2015.
 */
public class SelfiesRequest {
    private String TAG = this.getClass().getName();

    private OkHttpClient client = new OkHttpClient();
    private Context context;

    public SelfiesRequest(Context ctx)
    {
        context = ctx;
    }

    public Pair<String, ArrayList<InstagramData>> GetImages(String hashTag, String tag_id) {

        StringBuilder instagramGetApi = new StringBuilder("https://api.instagram.com/v1/tags/");
        hashTag = hashTag == null ? context.getResources().getString(R.string.DEFAULT_HASHTAG) : hashTag;
        instagramGetApi.append(hashTag);
        instagramGetApi.append("/media/recent?client_id=");
        instagramGetApi.append(context.getResources().getString(R.string.CLIENTID));
        String urlClean;
        if (tag_id != null) {
            instagramGetApi.append("&max_tag_id=");
            instagramGetApi.append(tag_id);
            urlClean = instagramGetApi.toString();
            urlClean = urlClean.replace("\"", "");
        } else {
            urlClean = instagramGetApi.toString();
        }
        return GetRequest(urlClean);
    }

    public Pair<String, ArrayList<InstagramData>> GetRequest(String url) {
        String nextPage = null;
        ArrayList<InstagramData> dataPack = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String response = run(url);
            JsonNode rootNode = mapper.readValue(response.getBytes(), JsonNode.class);
            JsonNode nextId = rootNode.get("pagination");
            if(nextId != null) {
                nextPage = nextId.get("next_max_tag_id").toString();
                ArrayNode dataJson = (ArrayNode) rootNode.get("data");
                for (JsonNode element : dataJson) {
                    InstagramData data = new InstagramData();
                    data.likesCount = element.get("likes").get("count").toString().replace("\"", "");
                    data.username = element.get("user").get("username").toString().replace("\"", "");
                    data.usernameProfileImage = element.get("user").get("profile_picture").toString().replace("\"", "");
                    data.imageUrl = element.get("images").get("standard_resolution").get("url").toString().replace("\"", "");
                    dataPack.add(data);
                }
            }
        } catch(IOException ex)
        {
            Log.d(TAG, ex.getMessage());
        }
        return new Pair<>(nextPage, dataPack);
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
