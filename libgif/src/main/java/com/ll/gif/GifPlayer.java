package com.ll.gif;

public class GifPlayer implements Runnable {

    private static final String TAG = GifPlayer.class.getSimpleName();
    private Thread playThread;
    private IGifView mGifView;
    private GifDecoder mGifDecoder;
    private SourceType mSourceType;
    private String mFilePath;
    private byte[] mSourceBuffer;
    private boolean mIsSourceOk;
    private volatile boolean mIsRunning;
    private volatile boolean mIsPlaying;
    private boolean mIsReverse;//是否反向

    public GifPlayer(IGifView gifView) {
        mGifView = gifView;
        mGifDecoder = new GifDecoder();
    }

    public void setFilePath(String filePath) {
        mSourceType = SourceType.SOURCE_PATH;
        mFilePath = filePath;
        mIsSourceOk = true;
    }

    public void setBuffer(byte[] buffer) {
        mSourceType = SourceType.SOURCE_BUFFER;
        mSourceBuffer = buffer;
        mIsSourceOk = true;
    }

    public void start() {
        if (mIsSourceOk) {
            playThread = new Thread(this);
            mIsRunning = true;
            mIsPlaying = true;
            playThread.start();
        }
    }

    public void stop() {
        mIsRunning = false;
    }

    public void pause() {
        mIsPlaying = false;
    }

    public void resume() {
        mIsPlaying = true;
    }

    public void reverse(boolean reverse){
        this.mIsReverse = reverse;
    }

    @Override
    public void run() {
        try {
            boolean isLoadOk;
            if (mSourceType == SourceType.SOURCE_PATH) {
                isLoadOk = mGifDecoder.load(mFilePath);
            } else {
                isLoadOk = mGifDecoder.load(mSourceBuffer);
            }
            mGifView.onLoadFinish(isLoadOk, mGifDecoder.getBitmap());
//            mGifDecoder.setCurIndex(-1);
            mGifDecoder.setCurIndex(100);
            while (mIsRunning) {
                if (mIsPlaying) {
                    int delay;
                    if (mIsReverse){
                        delay = mGifDecoder.decodePreFrame();
                    }else {
                        delay = mGifDecoder.decodeNextFrame();
                    }
                    mGifView.onRender();
                    Thread.sleep(delay);
                }
            }
            mGifDecoder.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    enum SourceType {SOURCE_PATH, SOURCE_BUFFER}
}
