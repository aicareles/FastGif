package com.ll.gif;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GifView extends ImageView implements IGifView {

    private static final int MAX_ASSETS_LENGTH = 0x10000000;
    private GifPlayer mGifPlayer;
    private String mAssetName;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGifPlayer = new GifPlayer(this);
    }

    public void loadFile(String filePath) {
        mGifPlayer.setFilePath(filePath);
    }

    public void loadIds(int resIds){
        byte[] bytes = getBytesById(resIds);
        mGifPlayer.setBuffer(bytes);
    }

    private byte[] getBytesById(int resId){
//        byte[] datas = new byte[3224];
        TypedArray array = getContext().getResources().obtainTypedArray(resId);
        int len = array.length();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), array.getResourceId(0, 0));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        array.recycle();
        return data;
    }

    public void loadAssets(String assetsName) {
        try {
            mAssetName = assetsName;
            InputStream is = getContext().getAssets().open(assetsName);
            if (is.available() <= MAX_ASSETS_LENGTH) {
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                mGifPlayer.setBuffer(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GifPlayer getGifPlayer(){
        return mGifPlayer;
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

    public String getAssetName() {
        return mAssetName;
    }
}
