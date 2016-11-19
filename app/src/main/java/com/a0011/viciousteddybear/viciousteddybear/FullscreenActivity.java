package com.a0011.viciousteddybear.viciousteddybear;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private View mControlsView;
    private boolean mVisible;
    int display_images = 0;
    int fps = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);


        ImageView sampleImage = (ImageView) findViewById(R.id.fullscreen_content);
        sampleImage.setBackgroundResource(R.drawable.sample1);
        sampleImage.setImageDrawable(getResources().getDrawable(R.layout.animation));
        AnimationDrawable frameAnimation = (AnimationDrawable) sampleImage.getDrawable();
        frameAnimation.start();
        //display_images = 1;

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        //animateBackground();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mVisible = false;
    }

//    public void animateBackground() {
//        long start;
//        start = System.currentTimeMillis();
//        while(true) {
//            ImageView sampleImage = (ImageView) findViewById(R.id.fullscreen_content);
//            if (display_images == 1) {
//                sampleImage.setBackgroundResource(R.drawable.sample1);
//            } else if (display_images == 2) {
//                sampleImage.setBackgroundResource(R.drawable.sample2);
//            } else if (display_images == 3) {
//                sampleImage.setBackgroundResource(R.drawable.sample3);
//            }
//            display_images++;
//            if (display_images >= 4) {
//                display_images = 1;
//            }
//            while((System.currentTimeMillis() - start) < 1000/(double)fps);
//            start = System.currentTimeMillis();
//        }
//    }

}
