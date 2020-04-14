package com.example.sportsstatisticsapplication;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import objects.Player;
import objects.Team;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

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
    private Switch teamSwitch;
    private TextView teamOneTextView;
    private TextView teamTwoTextView;
    private TableLayout tableLayout;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        tableLayout = findViewById(R.id.dataTable);
        teamSwitch = findViewById(R.id.teamSwitch);
        teamOneTextView = findViewById(R.id.teamOneTextView);
        teamTwoTextView = findViewById(R.id.teamTwoTextView);
        tableLayout.removeAllViews();
        Intent extras = getIntent();
        if (extras!= null) {
            Teams = (ArrayList<Team>) extras.getSerializableExtra("Teams");
        }
        teamOneTextView.setText(Teams.get(0).getName());
        teamTwoTextView.setText(Teams.get(1).getName());
        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

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

        fillTable();

        teamSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    currentTab = 1;
                }
                else
                {
                    currentTab = 0;
                }
                fillTable();
            }
        });
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
        //mControlsView.setVisibility(View.GONE);
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

    private void fillTable()
    {
        tableLayout.removeAllViews();
        int fromFree = 0;
        TableRow hRow=new TableRow(this);
        TextView hGoal = new TextView(this);
        hGoal.setTextColor(Color.WHITE);
        TextView hPoint=new TextView(this);
        hPoint.setTextColor(Color.WHITE);
        TextView hNumber = new TextView(this);
        hNumber.setTextColor(Color.WHITE);
        TextView hWide = new TextView(this);
        hWide.setTextColor(Color.WHITE);
        TextView hFreeFor=new TextView(this);
        hFreeFor.setTextColor(Color.WHITE);
        TextView hFreeAg = new TextView(this);
        hFreeAg.setTextColor(Color.WHITE);
        TextView hPossW=new TextView(this);
        hPossW.setTextColor(Color.WHITE);
        TextView hPossL = new TextView(this);
        hPossL.setTextColor(Color.WHITE);
        TextView hPass = new TextView(this);
        hPass.setTextColor(Color.WHITE);
        TextView hSixtyFive=new TextView(this);
        hSixtyFive.setTextColor(Color.WHITE);
        TextView hYellow = new TextView(this);
        hYellow.setTextColor(Color.WHITE);
        TextView hRed=new TextView(this);
        hRed.setTextColor(Color.WHITE);

        hGoal.setText("Goals\t");
        hPoint.setText("Points\t");
        hWide.setText("Wides\t");
        hNumber.setText("Jersey Number\t");
        hFreeFor.setText("Fouls Awarded\t");
        hFreeAg.setText("Fouls Conceded\t");
        hPossW.setText("Possession Won\t");
        hPossL.setText("Possession Lost\t");
        hPass.setText("Passes Received\t");
        hSixtyFive.setText("Sixty Fives Won\t");
        hYellow.setText("Yellow Cards\t");
        hRed.setText("Red Cards\t");

        /*row.addView(number);
            row.addView(goal);
            row.addView(point);
            row.addView(wide);
            row.addView(possW);
            row.addView(possL);
            row.addView(pass);
            row.addView(freeFor);
            row.addView(freeAg);
            row.addView(sixtyFive);
            row.addView(yellow);
            row.addView(red);
*/

        hRow.addView(hNumber);
        hRow.addView(hGoal);
        hRow.addView(hPoint);
        hRow.addView(hWide);
        hRow.addView(hPossW);
        hRow.addView(hPossL);
        hRow.addView(hPass);
        hRow.addView(hFreeFor);
        hRow.addView(hFreeAg);
        hRow.addView(hSixtyFive);
        hRow.addView(hYellow);
        hRow.addView(hRed);



        tableLayout.addView(hRow);



        for(int i=1;i<=Teams.get(currentTab).size();i++)
        {
            TableRow row=new TableRow(this);
            TextView goal = new TextView(this);
            goal.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            goal.setTextColor(Color.WHITE);
            TextView point=new TextView(this);
            point.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            point.setTextColor(Color.WHITE);
            TextView wide = new TextView(this);
            wide.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            wide.setTextColor(Color.WHITE);
            TextView number = new TextView(this);
            number.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            number.setTextColor(Color.WHITE);
            TextView freeFor=new TextView(this);
            freeFor.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            freeFor.setTextColor(Color.WHITE);
            TextView freeAg = new TextView(this);
            freeAg.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            freeAg.setTextColor(Color.WHITE);
            TextView possW=new TextView(this);
            possW.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            possW.setTextColor(Color.WHITE);
            TextView possL = new TextView(this);
            possL.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            possL.setTextColor(Color.WHITE);
            TextView pass = new TextView(this);
            pass.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            pass.setTextColor(Color.WHITE);
            TextView sixtyFive=new TextView(this);
            sixtyFive.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            sixtyFive.setTextColor(Color.WHITE);
            TextView yellow = new TextView(this);
            yellow.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            yellow.setTextColor(Color.WHITE);
            TextView red=new TextView(this);
            red.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            red.setTextColor(Color.WHITE);

            row.removeAllViews();
            fromFree = 0;
            number.setText("\t"+Teams.get(currentTab).getPlayer(i).getJerseyNumber()+"");
            number.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            number.setTextColor(Color.WHITE);

            goal.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("goal")+"");
            fromFree = Teams.get(currentTab).getPlayer(i).getStat("goalff");
            if (fromFree > 0)
            {
                goal.setText(goal.getText()+" ("+fromFree+")");
            }

            point.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("point")+"");
            fromFree = Teams.get(currentTab).getPlayer(i).getStat("pointff");
            if (fromFree > 0)
            {
                point.setText(point.getText()+" ("+fromFree+")");
            }

            possW.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("poss won")+"");

            possL.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("poss lost")+"");

            pass.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("pass")+"");

            wide.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("wide")+"");

            freeFor.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("free award")+"");

            freeAg.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("free concede")+"");

            sixtyFive.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("65")+"");

            yellow.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("yellow")+"");

            red.setText("\t"+Teams.get(currentTab).getPlayer(i).getStat("red")+"");

            row.addView(number);
            row.addView(goal);
            row.addView(point);
            row.addView(wide);
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

    public void sendEmail(View view) {

        String output = "Player,Goals,Goals From Placed Balls,Points,Points From Placed Balls,Wides,Possessions Won,Possessions Lost,Passes Completed,Frees Won,Frees Conceded,65s Won,Yellow Cards,Red Cards\n";
        for (int i = 0; i < Teams.size(); i++)
        {
            for (int j = 1; j <= Teams.get(0).size(); j++)
            {
                output += Teams.get(i).getName() + " " + Teams.get(i).getPlayer(j).getJerseyNumber() + ",";
                output += Teams.get(i).getPlayer(j).getStat("goal") + ",";
                output += Teams.get(i).getPlayer(j).getStat("goalff") + ",";
                output += Teams.get(i).getPlayer(j).getStat("point") + ",";
                output += Teams.get(i).getPlayer(j).getStat("pointff") + ",";
                output += Teams.get(i).getPlayer(j).getStat("wide") + ",";
                output += Teams.get(i).getPlayer(j).getStat("poss won") + ",";
                output += Teams.get(i).getPlayer(j).getStat("poss lost") + ",";
                output += Teams.get(i).getPlayer(j).getStat("pass") + ",";
                output += Teams.get(i).getPlayer(j).getStat("free award") + ",";
                output += Teams.get(i).getPlayer(j).getStat("free concede") + ",";
                output += Teams.get(i).getPlayer(j).getStat("65") + ",";
                output += Teams.get(i).getPlayer(j).getStat("yellow") + ",";
                output += Teams.get(i).getPlayer(j).getStat("red") + "\n";
            }
        }
        output+=("\n\n\n To add stats to an excel sheet, save above data to a \".txt\" file and see https://support.office.com/en-ie/article/import-or-export-text-txt-or-csv-files-5250ac4c-663c-47ce-937b-339e391393ba");


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, output);
        intent.putExtra(Intent.EXTRA_SUBJECT, (Teams.get(0).getName()+" vs "+Teams.get(1).getName()+" stats"));
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Send via:"));
    }
}
