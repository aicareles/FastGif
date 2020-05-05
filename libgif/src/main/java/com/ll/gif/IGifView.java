package com.ll.gif;

import android.graphics.Bitmap;


public interface IGifView {
    void onLoadFinish(boolean isLoadOk, Bitmap bitmap);
    void onRender();
}
