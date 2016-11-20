package com.a0011.viciousteddybear.viciousteddybear;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.SensorEventListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.content.Context;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import java.io.IOException;
import android.media.MediaPlayer;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PlayingGame extends AppCompatActivity {
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private View mControlsView;
    private boolean mVisible;
    int points = 0;
    private Handler handler = new Handler();
    MediaPlayer zPlayer;
    MediaPlayer mPlayer;
    int intValue;

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > 50) {
                points += 1;
                TextView scoresheet = (TextView) findViewById(R.id.Scoreboard);
                scoresheet.setText(String.valueOf(points));
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            mPlayer = MediaPlayer.create(this, R.raw.ouch);
            mPlayer.start();
            ImageView sampleImage;
            points += 1;
            TextView scoresheet = (TextView) findViewById(R.id.Scoreboard);
            scoresheet.setText(String.valueOf(points));
            if (intValue == 0) {
                String uri = "@drawable/trump2";  // where myresource (without the extension) is the file

                int imageResource;
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        int imageResource;
                        String uri = "@drawable/trump1";
                        ImageView sampleImage;
                        imageResource = getResources().getIdentifier(uri, null, getPackageName());
                        sampleImage = (ImageView) findViewById(R.id.custom_image);
                        Drawable res = getResources().getDrawable(imageResource);
                        sampleImage.setImageDrawable(res);
                    }
                }, 100); //10sec delay

                uri = "@drawable/trump2";  // where myresource (without the extension) is the file
                imageResource = getResources().getIdentifier(uri, null, getPackageName());
                sampleImage = (ImageView) findViewById(R.id.custom_image);
                Drawable res = getResources().getDrawable(imageResource);
                sampleImage.setImageDrawable(res);
            }
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("custom_image", 0);

        setContentView(R.layout.activity_playing_game);

        zPlayer = MediaPlayer.create(this, R.raw.ppap);
        zPlayer.start();

        Context context = this;
        Bitmap bit = null;
        ImageView sampleImage;
        try {
            if (intValue == 1) {
                bit = BitmapFactory.decodeStream(context.openFileInput("myImage"));
                sampleImage = (ImageView) findViewById(R.id.custom_image);
                sampleImage.setImageDrawable(new BitmapDrawable(getResources(), bit));
                sampleImage = (ImageView) findViewById(R.id.fullscreen_content);
                sampleImage.setImageDrawable(getResources().getDrawable(R.layout.animation_not_hit));
                AnimationDrawable frameAnimation = (AnimationDrawable) sampleImage.getDrawable();
                frameAnimation.start();
            }
//            sampleImage.setBackgroundResource(R.drawable.sample2);
//            sampleImage.setImageDrawable(getResources().getDrawable(R.layout.playing_game_animation_1));
//            AnimationDrawable frameAnimation = (AnimationDrawable) sampleImage.getDrawable();
//            frameAnimation.start();
        }catch(IOException e) {
            return;
        }
        if (intValue == 0) {
            String uri = "@drawable/trump1";  // where myresource (without the extension) is the file

            int imageResource = getResources().getIdentifier(uri, null, getPackageName());

            sampleImage = (ImageView) findViewById(R.id.fullscreen_content);
            sampleImage.setBackgroundResource(R.drawable.sample1);
            sampleImage.setImageDrawable(getResources().getDrawable(R.layout.animation_not_hit));
            AnimationDrawable frameAnimation = (AnimationDrawable) sampleImage.getDrawable();
            sampleImage = (ImageView) findViewById(R.id.custom_image);
            Drawable res = getResources().getDrawable(imageResource);
            sampleImage.setImageDrawable(res);
            frameAnimation.start();
        }


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
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

    public void onDestroy() {

        zPlayer.stop();
        super.onDestroy();

    }

}
