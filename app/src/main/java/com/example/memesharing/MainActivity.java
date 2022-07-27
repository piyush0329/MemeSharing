package com.example.memesharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            //  Block of code to try
            loadMeme();
        }
        catch(Exception e) {
            //  Block of code to handle errors
            System.out.println(e);
        }

    }

    String url = "https://meme-api.herokuapp.com/gimme";

    private void loadMeme() {

        JSONObject obj = new JSONObject();

        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url, null,
                new Response.Listener < JSONObject > () {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                             String string = response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ImageView imageView = (ImageView) findViewById(R.id.memeImageView);
                        Glide.with(MainActivity.this).load(url).into(imageView);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
    public void shareMeme(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"hey check out this cool meme "+ url);
          Intent   chooser = Intent.createChooser(intent,"share using...");
          startActivity(chooser);

    }

    public void nextMeme(View view) {
       loadMeme();
    }
}