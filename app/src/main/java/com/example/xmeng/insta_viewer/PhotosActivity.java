package com.example.xmeng.insta_viewer;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static final String client_id = "5028c71a9b454a5f8b8b885c09a14e19";
    public ArrayList<InstagramPhoto> instaPhoto;
    public PhotoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        instaPhoto = new ArrayList<InstagramPhoto>();
        adapter = new PhotoAdapter(this, instaPhoto);
        ListView lv = (ListView) findViewById(R.id.lvPhotos);
        lv.setAdapter(adapter);
        fetchPopularPhotos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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

    public void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + client_id;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("debug", response.toString());
                JSONArray photoJson = null;
                try {
                    photoJson = response.getJSONArray("data");
                    for(int i = 0 ; i < photoJson.length(); i ++ ){
                        JSONObject singlePhotoJson = photoJson.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = singlePhotoJson.getJSONObject("user").getString("username");
                        photo.userProfile = singlePhotoJson.getJSONObject("user").getString("profile_picture");
                        photo.caption = singlePhotoJson.getJSONObject("caption").getString("text");
                        photo.imageUrl = singlePhotoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = singlePhotoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = singlePhotoJson.getJSONObject("likes").getInt("count");
                        instaPhoto.add(photo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });

    }
}
