package com.example.sportsstatisticsapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

import objects.Game;
import objects.TextParse;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private TextView txtView;
    private Chronometer chronometer;
    private long pauseOffset;
    private Button startBtn;
    private Button stopBtn;
    private Button resetBtn;
    private Button helpBtn;
    private Button resetGameBtn;
    private Button undoBtn;
    private Button viewDataBtn;
    private TextView teamOneTV;
    private TextView teamTwoTV;
    private TextView teamOneScore;
    private TextView teamTwoScore;
    private TextView textViewOne;
    private TextView textViewTwo;
    private TextView textViewThree;
    private TextView textViewFour;
    private TextView textViewFive;
    private AlertDialog editTextDialog;
    private AlertDialog helpDialog;
    private EditText editText;
    private TextView teamTextView;
    private Game game;
    private TextParse textParse = new TextParse();
    private static final boolean AUTO_HIDE = true;
    private LinearLayout popoutLayout;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar
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
            mControlsView.setVisibility(View.GONE);
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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        //import all items from the layout file
        chronometer = findViewById(R.id.chronometer);
        startBtn = findViewById(R.id.startBtn);
        stopBtn = findViewById(R.id.stopBtn);
        resetBtn = findViewById(R.id.resetBtn);
        teamOneTV = findViewById(R.id.team_one);
        teamTwoTV = findViewById(R.id.team_two);
        teamOneScore = findViewById(R.id.teamOneScore);
        teamTwoScore = findViewById(R.id.teamTwoScore);
        FloatingActionButton fAB = findViewById(R.id.infoButton);
        popoutLayout = findViewById(R.id.popoutLayout);
        helpBtn = findViewById(R.id.helpButton);
        undoBtn = findViewById(R.id.undoButton);
        resetGameBtn = findViewById(R.id.resetButton);
        viewDataBtn = findViewById(R.id.viewButton);
        textViewOne = findViewById(R.id.TextView1);
        textViewTwo = findViewById(R.id.TextView2);
        textViewThree = findViewById(R.id.TextView3);
        textViewFour = findViewById(R.id.TextView4);
        textViewFive = findViewById(R.id.TextView5);

        editTextDialog = new AlertDialog.Builder(this).create();
        helpDialog = new AlertDialog.Builder(this).create();

        editText = new EditText(this);
        String[] teamNames = {teamOneTV.getText().toString(), teamTwoTV.getText().toString()}; //get current team name values and set the Team object names
        game = new Game(teamNames, 24);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
        { //Listener for every tick of the stopwatch
            public void onChronometerTick(Chronometer chronometer)
                {
                    //on every chronometer tick, the scores will update
                    teamOneScore.setText(game.returnTeam(0).getGoals() + "-" + String.format("%02d",game.returnTeam(0).getPoints()));
                    teamTwoScore.setText(game.returnTeam(1).getGoals() + "-" + String.format("%02d",game.returnTeam(1).getPoints()));

                }
        }                                       );
        //create the dialog for changing team names
        editTextDialog.setTitle("Change Team Name");
        editTextDialog.setView(editText);
        editTextDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                teamTextView.setText(editText.getText());
                game.returnTeam(0).changeName(teamOneTV.getText().toString());
                game.returnTeam(1).changeName(teamTwoTV.getText().toString());
            }
        });

        //on click for the floating action button (bottom right)
        fAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popoutLayout.getVisibility() == View.VISIBLE)
                {
                    popoutLayout.setVisibility(View.INVISIBLE);
                }
                else {
                        popoutLayout.setVisibility(View.VISIBLE);
                        helpBtn.setVisibility(View.VISIBLE);
                        undoBtn.setVisibility(View.VISIBLE);
                        viewDataBtn.setVisibility(View.VISIBLE);
                        resetGameBtn.setVisibility(View.VISIBLE);
                     }
            }
        });
        //if help pressed, open help page
        helpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                helpDialog.setTitle("Help");
                helpDialog.setMessage(getResources().getString(R.string.message));

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        helpDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                helpDialog.hide();
                                popoutLayout.setVisibility(View.INVISIBLE);
                            }
                        });



                                // A null listener allows the button to dismiss the dialog and take no further action.

                helpDialog.setIcon(android.R.drawable.ic_dialog_alert);
                helpDialog.show();
            }
        });
        //if undo pressed, undo last stat
        undoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                popoutLayout.setVisibility(View.INVISIBLE);
                textParse.undoStat(game.returnArray());
                undoStatTexts();
                Toast toast=Toast. makeText(getApplicationContext(),"Last stat undone",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        //if reset pressed, reset game data
        resetGameBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast=Toast.makeText(getApplicationContext(),"Game reset",Toast.LENGTH_SHORT);
                toast.show();
                popoutLayout.setVisibility(View.INVISIBLE);
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                stopBtn.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                chronometer.stop();
                resetStatTexts();
                for (int i = 0; i< 2; i++)
                {
                    game.returnTeam(i).clear();
                }

                teamOneScore.setText(game.returnTeam(0).getGoals() + "-" + String.format("%02d",game.returnTeam(0).getPoints()));
                teamTwoScore.setText(game.returnTeam(1).getGoals() + "-" + String.format("%02d",game.returnTeam(1).getPoints()));

            }
        });
        //if view data pressed, open data table page
        viewDataBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        popoutLayout.setVisibility(View.INVISIBLE);
                        Intent tableIntent = new Intent(FullscreenActivity.this, tableActivity.class);
                        tableIntent.putExtra("Teams", game.returnArray());
                        startActivity(tableIntent);
                    }
                });


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.


    }
    //handler for resetting the recent stats text objects
    private void resetStatTexts() {
        textViewOne.setText("");
        textViewTwo.setText("");
        textViewThree.setText("");
        textViewFour.setText("");
        textViewFive.setText("");
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
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
    //recognizerIntent function, called when microphone image or headset media button is pressed
    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.getDefault()); //Locale.getDefault()
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IE");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speech_prompt));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 12);
        } else {
            Toast.makeText(this, "This device does not support speech input", Toast.LENGTH_SHORT).show();
        }
    }


    @Override //if you get a value returned from the recognizerIntent
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //if codes match (no error)
        if (requestCode == 12)
        {   //parse text
            if (resultCode == RESULT_OK && data != null)
            {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                textParse.ParseText(result.get(0), game.returnArray());
                //if the parsed text is a new statistic, log it
                if (textParse.isNewStat()) {
                    setStatTexts(textParse.getLastStat().toString());
                }
                else { //incorrect input, show error toast
                    Toast.makeText(getApplicationContext(), "Incorrect input", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
    //handler for adding new stat to the recent stats text
    private void setStatTexts(String newestStat) {
        textViewFive.setText(textViewFour.getText());
        textViewFour.setText(textViewThree.getText());
        textViewThree.setText(textViewTwo.getText());
        textViewTwo.setText(textViewOne.getText());
        textViewOne.setText(newestStat);
    }
    //handler for removing most recent stat from the recent stats text
    private void undoStatTexts()
    {
        textViewOne.setText(textViewTwo.getText());
        textViewTwo.setText(textViewThree.getText());
        textViewThree.setText(textViewFour.getText());
        textViewFour.setText(textViewFive.getText());
        textViewFive.setText("");
    }

    //Chronometer start pressed
    public void startChronometer(View view) {

            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            startBtn.setVisibility(View.GONE);
            resetBtn.setVisibility(View.VISIBLE);
            stopBtn.setVisibility(View.VISIBLE);
    }
    //Chronometer reset pressed
    public void resetChronometer(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                stopBtn.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                //minPicker.setVisibility(View.VISIBLE);
                //secPicker.setVisibility(View.VISIBLE);
                chronometer.stop();

            //1 second = 1000
    }
    //Chronometer stop pressed
    public void stopChronometer(View view) {

            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            stopBtn.setVisibility(View.GONE);
            startBtn.setVisibility(View.VISIBLE);
    }
    //Team One (left) name has been pressed
    public void changeTeamOne(View view) {
        teamTextView = teamOneTV;
        changeText();
    }
    //Team Two (right) name has been pressed
    public void changeTeamTwo(View view) {
        teamTextView = teamTwoTV;
        changeText();
    }

    //Change the text for the team name
    public void changeText() {
        editText.setText(teamTextView.getText());
        editTextDialog.show();
    }
    //give headset media button same functionality as the microphone image
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HEADSETHOOK){
            getSpeechInput(new View(this));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
