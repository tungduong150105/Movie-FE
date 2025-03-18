package com.example.movieapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.Adapters.SliderAdapters;
import com.example.movieapp.Domain.SliderItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPagers2Banner;
    private SliderAdapters sliderAdapters;
    private List<SliderItems> sliderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        viewPagers2Banner = findViewById(R.id.viewPager2Banner);

        sliderItems = new ArrayList<>();

        try {
            fetch_images(() -> {
                sliderAdapters = new SliderAdapters(sliderItems, viewPagers2Banner, getApplicationContext());
                viewPagers2Banner.setAdapter(sliderAdapters);

                viewPagers2Banner.setOffscreenPageLimit(3);
                viewPagers2Banner.setClipChildren(false);
                viewPagers2Banner.setClipToPadding(false);

                viewPagers2Banner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                CompositePageTransformer transformer = new CompositePageTransformer();
                transformer.addTransformer(new MarginPageTransformer(40));
                transformer.addTransformer((page, position) -> {
                   float r = 1 - Math.abs(position);
                   page.setScaleY(0.85f + r * 0.15f);
                });

                viewPagers2Banner.setPageTransformer(transformer);
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public interface VolleyCallBack {
        void onSuccess();
    }

    public void fetch_images(final VolleyCallBack callBack) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://movie-be-0sp3.onrender.com/movies";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            response -> {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String imageUrl = jsonObject.getString("image_url");
                        sliderItems.add(new SliderItems(imageUrl));
                    }
                    callBack.onSuccess();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            },
            error -> Toast.makeText(this, "Can't fetch images", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }
}