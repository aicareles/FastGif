package com.ll.gifshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.github.chrisbanes.photoview.PhotoView;
import com.ll.gif.IGifView;
import com.ll.gif.GifPlayer;

import java.io.IOException;
import java.io.InputStream;


public class GifPhotoView extends PhotoView implements IGifView {

    private static final int MAX_ASSETS_LENGTH = 0x10000000;
    private GifPlayer mGifPlayer;

    public GifPhotoView(Context context) {
        this(context, null);
    }

    public GifPhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public GifPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        initPlayer();
    }

    private void initPlayer() {
        mGifPlayer = new GifPlayer(this);
    }

    public void loadAssets(String assetsPath) {
        try {
            InputStream is = getContext().getAssets().open(assetsPath);
            if (is.available() <= MAX_ASSETS_LENGTH) {
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                mGifPlayer.setBuffer(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mGifPlayer.pause();
    }

    public void resume() {
        mGifPlayer.resume();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mGifPlayer.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mGifPlayer.stop();
    }

    @Override
    public void onLoadFinish(boolean isLoadOk, final Bitmap bitmap) {
        if (isLoadOk) {
            post(new Runnable() {
                @Override
                public void run() {
                    setImageBitmap(bitmap);
                }
            });
        }
    }

    @Override
    public void onRender() {
        postInvalidate();
    }
}
