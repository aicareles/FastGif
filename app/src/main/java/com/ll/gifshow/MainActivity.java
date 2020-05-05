package com.ll.gifshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ll.gif.FrameView;
import com.ll.gif.GifView;

public class MainActivity extends AppCompatActivity {

    private GifView mGifView1;
    private GifView mGifView2;
    private GifView mGifView3;
    private GifView mGifView4;
    private FrameView mGifView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGifView1 = findViewById(R.id.demo_gif_view1);
        mGifView2 = findViewById(R.id.demo_gif_view2);
        mGifView3 = findViewById(R.id.demo_gif_view3);
        mGifView4 = findViewById(R.id.demo_gif_view4);
        mGifView5 = findViewById(R.id.demo_gif_view5);
        mGifView1.loadAssets("test1.gif");
        mGifView2.loadAssets("test2.gif");
        mGifView3.loadAssets("test3.gif");
        mGifView4.loadAssets("gif_bat_01.gif");

        mGifView5.setBitmapResourcesId(R.array.music_playing);
        mGifView5.start();

        mGifView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPhotoView("test1.gif");
            }
        });
        mGifView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPhotoView("test2.gif");
            }
        });
        mGifView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPhotoView("test3.gif");
            }
        });
    }

    private void startPhotoView(String assetName) {
        Intent intent = new Intent(this, PhotoViewActivity.class);
        intent.putExtra("asset_name", assetName);
        startActivity(intent);
    }
}
