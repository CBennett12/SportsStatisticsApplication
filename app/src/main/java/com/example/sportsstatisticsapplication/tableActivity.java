package com.example.sportsstatisticsapplication;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import objects.Team;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class tableActivity extends AppCompatActivity {
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
    private int currentTab = 0;
    private ArrayList<Team> Teams;
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
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
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

    public Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        TableLayout tableLayout = findViewById(R.id.dataTable);
        Intent extras = getIntent();
        if (extras!= null) {
            Teams = (ArrayList<Team>) extras.getSerializableExtra("Teams");
        }

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        closeButton = findViewById(R.id.close_button);

        // Set up the user interaction to manually show or hide the system UI.
        /*mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        fillTable(tableLayout);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

     /*private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }*/


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
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void fillTable(TableLayout tableLayout)
    {
        tableLayout.removeAllViews();
        int fromFree = 0;

        TextView goal = new TextView(this);
        TextView point=new TextView(this);
        TextView number = new TextView(this);
        TextView freeFor=new TextView(this);
        TextView freeAg = new TextView(this);
        TextView possW=new TextView(this);
        TextView possL = new TextView(this);
        TextView pass = new TextView(this);
        TextView sixtyFive=new TextView(this);
        TextView yellow = new TextView(this);
        TextView red=new TextView(this);

        for(int i=0;i<Teams.size();i++)
        {
            TableRow row=new TableRow(this);
            fromFree = 0;
            number.setText(Teams.get(currentTab).getPlayer(i).getStat("number")+"");

            goal.setText(Teams.get(currentTab).getPlayer(i).getStat("goal")+"");
            fromFree = Teams.get(currentTab).getPlayer(i).getStat("goalff");
            if (fromFree > 0)
            {
                goal.setText(goal.getText()+" ("+fromFree+")");
            }

            point.setText(Teams.get(currentTab).getPlayer(i).getStat("point")+"");
            fromFree = Teams.get(currentTab).getPlayer(i).getStat("pointff");
            if (fromFree > 0)
            {
                point.setText(point.getText()+" ("+fromFree+")");
            }

            possW.setText(Teams.get(currentTab).getPlayer(i).getStat("poss won")+"");

            possL.setText(Teams.get(currentTab).getPlayer(i).getStat("poss lost")+"");

            pass.setText(Teams.get(currentTab).getPlayer(i).getStat("pass")+"");

            freeFor.setText(Teams.get(currentTab).getPlayer(i).getStat("free award")+"");

            freeAg.setText(Teams.get(currentTab).getPlayer(i).getStat("free concede")+"");

            sixtyFive.setText(Teams.get(currentTab).getPlayer(i).getStat("65")+"");

            yellow.setText(Teams.get(currentTab).getPlayer(i).getStat("yellow")+"");

            red.setText(Teams.get(currentTab).getPlayer(i).getStat("red")+"");

            row.addView(number);
            row.addView(goal);
            row.addView(point);
            row.addView(possW);
            row.addView(possL);
            row.addView(pass);
            row.addView(freeFor);
            row.addView(freeAg);
            row.addView(sixtyFive);
            row.addView(yellow);
            row.addView(red);

            tableLayout.addView(row);
        }
    }
}
