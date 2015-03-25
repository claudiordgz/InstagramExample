package dev.claudiordgz.instragramexample;

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

    public Pair<String, ArrayList<String>> GetImages(String hashTag, String tag_id) {
        ArrayList<String> images = new ArrayList<>();
        String nextPage = null;
        StringBuilder instagramGetApi = new StringBuilder("https://api.instagram.com/v1/tags/");
        hashTag = hashTag == null ? context.getResources().getString(R.string.DEFAULT_HASHTAG) : hashTag;
        instagramGetApi.append(hashTag);
        instagramGetApi.append("/media/recent?client_id=");
        instagramGetApi.append(context.getResources().getString(R.string.CLIENTID));
        String urlClean;
        if(tag_id != null){
            instagramGetApi.append("&max_tag_id=");
            instagramGetApi.append(tag_id);
            urlClean = instagramGetApi.toString();
            urlClean = urlClean.replace("\"", "");
        } else {
            urlClean = instagramGetApi.toString();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            String response = run(urlClean);
            JsonNode rootNode = mapper.readValue(response.getBytes(), JsonNode.class);
            nextPage = rootNode.get("pagination").get("next_max_tag_id").toString();
            ArrayNode data = (ArrayNode)rootNode.get("data");
            for(JsonNode element : data) {
                JsonNode image = element.get("images").get("standard_resolution").get("url");
                images.add(image.toString());
            }
        } catch(IOException ex)
        {
            Log.d(TAG, ex.getMessage());
        }
        Pair<String, ArrayList<String>> returnVal = new Pair<>(nextPage, images);
        return returnVal;
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
