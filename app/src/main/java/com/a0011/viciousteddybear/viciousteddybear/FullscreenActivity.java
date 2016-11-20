package com.a0011.viciousteddybear.viciousteddybear;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;



/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    float x_coord;
    float y_coord;
    ImageView punchImage;
    Runnable mRunnable;
    Handler punchHandler = new Handler();


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

//    public void startPunching()
//    {
//        punchImage = (ImageView) findViewById(R.id.fullscreen_content);
//        punchImage.setOnClickListener(new onClickLister() {
//            @Override
//            public void onClick(View v){
//                punchImage.setBackgroundResource(R.drawable.punch1);
//                punchImage.setImageDrawable(getResources().getDrawable(R.layout.fullscreen_content));
//            }
//        });
//    }
//
//    public void startPunching(/*int punchCount*/)
//    {
//        AnimationDrawable punchAnimation;
//        //for(int i=0;i<punchCount;i++) {
//        ImageView punchImage = (ImageView) findViewById(R.id.fullscreen_content);
//        punchImage.setBackgroundResource(R.drawable.punch1);
//        punchImage.setImageDrawable(getResources().getDrawable(R.layout.punchanimation));
//        punchAnimation = (AnimationDrawable) punchImage.getDrawable();
//        punchAnimation.start();
////            ImageView punchImage = (ImageVieonw) findViewById(R.id.punch2);
////            punchImage.setBackgroundResource(R.drawable.punch2);
////            ImageView punchImage = (ImageView) findViewById(R.id.punch3);
////            punchImage.setBackgroundResource(R.drawable.punch3);
//        //}
//    }
//
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x_coord = event.getX();
                y_coord = event.getY();
                placeImage(x_coord, y_coord);
                punchImage.setVisibility(View.VISIBLE);
                punchHandler.removeCallbacks(mRunnable);
                punchHandler.postDelayed(mRunnable, 1000);
                break;
        }
        return true;
    }

    private void placeImage(float x_coord, float y_coord)
    {
        //placing at bottom right of touch
        //punchImage.layout(x_coord, y_coord, x_coord+48, y_coord+48);

        //placing at the centre of touch
        float viewWidth = (punchImage.getWidth()/2);
        float viewHeight = (punchImage.getHeight()/2);
        punchImage.layout((int)(x_coord-viewWidth), (int)(y_coord-viewHeight), (int)(x_coord+viewWidth), (int)(y_coord+viewHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //int punchCount = 100;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        punchImage = (ImageView) findViewById(R.id.fullscreen_content);
        punchImage.setVisibility(View.INVISIBLE); //initially invisible

        mRunnable = new Runnable() {
            @Override
            public void run() {
                punchImage.setVisibility(View.INVISIBLE); //If you want just hide the View. But it will retain space occupied by the View.
                punchImage.setVisibility(View.GONE); //This will remove the View. and free s the space occupied by the View
            }
        };


//        startPunching(/*punchCount*/);


        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }







//    private void toggle() {
//        if (mVisible) {
//            hide();
//        } else {
//            show();
//        }
//    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
