package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    private String userId;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Bitmap> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed_acivity);

        Intent intent = getIntent();
        userId = intent.getBundleExtra("bundle").getString("userId");

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Image");
        parseQuery.whereEqualTo("username",userId);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && !objects.isEmpty()){
                    for(ParseObject image : objects){
                        try {
                            byte[] file = image.getParseFile("image").getData();
                            ByteArrayInputStream inputStream = new ByteArrayInputStream(file);
                            Bitmap decoded = BitmapFactory.decodeStream(inputStream);
                            images.add(decoded);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    }
                }else
                    Log.i("Failed", "Received no Images");

                setRecylerView(images);
            }
        });

    }

    public void setRecylerView(List<Bitmap> images){
        adapter = new UserFeedAdapter(images);
        recyclerView = (RecyclerView) findViewById(R.id.rv_feed);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserFeedActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
