package com.lightningrobotics.scout_862;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import java.io.File;

import static com.lightningrobotics.scout_862.CycleData.makeCycleArrays;
import static com.lightningrobotics.scout_862.FileUtils.appData;
import static com.lightningrobotics.scout_862.FileUtils.matchCounter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener{

    FileUtils fileUtils = new FileUtils();
    ImageUtils imageUtils = new ImageUtils(this);
    AutoActivity autoActivity = new AutoActivity();
    TeleopActivity teleopActivity = new TeleopActivity();
    EndActivity endActivity = new EndActivity();
    public File extPath;
    Button lastMatchButton;
    Button nextMatchButton;
    TextView allianceText;
    TextView allianceNumber;
    TextView matchNumber;
    TextView teamNumber;
    EditText scouterName;
    EditText path;
    ImageView robotPic;
    TabLayout tabLayout;
    public static String PACKAGE_NAME;
    public static boolean importedData = false;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 300;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 1;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.colorFirstBlack
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
//            mControlsView.setVisibility(View.VISIBLE);
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
        setContentView(R.layout.activity_fullscreen);
        //Set Package name
        PACKAGE_NAME = getApplicationContext().getPackageName();

        extPath = new File(this.getExternalFilesDir(null), "data.xls");
        lastMatchButton = (Button) findViewById(R.id.btn_previous_match);
        nextMatchButton = (Button) findViewById(R.id.btn_next_match);
        allianceText = (TextView) findViewById(R.id.tv_alliance_text);
        allianceNumber = (TextView) findViewById(R.id.tv_alliance_position);
        matchNumber = (TextView) findViewById(R.id.tv_match_number);
        teamNumber = (TextView) findViewById(R.id.tv_team_number);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        scouterName = (EditText) findViewById(R.id.et_scouter_name);
        path = (EditText) findViewById(R.id.et_path);
        robotPic = (ImageView) findViewById(R.id.iv_robot_picture);
        lastMatchButton.setOnClickListener(this);
        nextMatchButton.setOnClickListener(this);
        allianceText.setOnClickListener(this);

        mVisible = true;
        //mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.iv_robot_picture);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.iv_robot_picture).setOnTouchListener(mDelayHideTouchListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Auto"));
        tabLayout.addTab(tabLayout.newTab().setText("Teleop"));
        tabLayout.addTab(tabLayout.newTab().setText("Robot Details"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

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
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void onClick(View v) {
        if (v == allianceText) {
            makeCycleArrays();
            File file = new File(path.getText().toString());
            if (file.exists())
                importData();
            else
                Toast.makeText(getApplicationContext(), path.toString() + " does not exist",
                        Toast.LENGTH_LONG).show();
        }

        if (importedData != false) {
            if ((v == nextMatchButton && matchCounter < 100) || (v == lastMatchButton && matchCounter > 1)) {
                //Write all your values to a 2D array
                writeMain();
                autoActivity.writeAuto();
                teleopActivity.writeTeleop();
                endActivity.writeEnd();

                //Copy that array to an excel sheet
                fileUtils.arrayToExcel(getPath(), extPath);

                if (v == nextMatchButton)
                    matchCounter++;
                if (v == lastMatchButton)
                    matchCounter--;
                autoActivity.resetAutoVisibility();
                teleopActivity.resetTeleopVisibility();
                //Read the values from an array
                readMain();
                autoActivity.readAuto();
                teleopActivity.readTeleop();
                endActivity.readEnd();
            }
        } else {
            matchCounter = 0;
            Toast.makeText(getApplicationContext(), "Import Data by pressing \"Alliance\"",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void writeMain()
    {
        //copy data from the excel sheet to an array
        appData = fileUtils.excelToArray(getPath(), extPath);
        appData[1][matchCounter] = scouterName.getText().toString();
    }

    public void readMain()
    {
        matchNumber.setText(String.format("%01d",matchCounter));
        teamNumber.setText(appData[0][matchCounter]);
        scouterName.setText(appData[1][matchCounter]);
        //Set and get image
        imageUtils.setImageId(appData[0][matchCounter]);

        if(imageUtils.getImageId() == 0)
            robotPic.setImageResource(imageUtils.getDefaultImageId());
        else
            robotPic.setImageResource(imageUtils.getImageId());
    }

    public void importData()
    {
        //Set the "importedData" flag to true
        importedData = true;
        //copy data from the excel sheet to an array
        appData = fileUtils.excelToArray(getPath(), extPath);
        //the cell at the position 0,0 is the alliance number
        allianceNumber.setText(appData[0][0]);
        //Switch statement to change the background of the top view
        switch(appData[0][0].toLowerCase().charAt(0))
        {
            case 'r': tabLayout.setBackgroundColor(getResources().getColor(R.color.colorFirstRed));
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorFirstCCtwo));
                break;
            case 'b': tabLayout.setBackgroundColor(getResources().getColor(R.color.colorFirstBlue));
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorFirstCCfour));
                break;
            default: tabLayout.setBackgroundColor(getResources().getColor(R.color.colorFirstCCeight));
                break;
        }
        //the value in the first collumn is for the current team
        teamNumber.setText(appData[0][matchCounter]);
        scouterName.setText(appData[1][matchCounter]);
        matchNumber.setText(String.format("%01d",matchCounter));
        //Set and get image
        imageUtils.setImageId(appData[0][matchCounter]);

        if(imageUtils.getImageId() == 0)
            robotPic.setImageResource(imageUtils.getDefaultImageId());
        else
            robotPic.setImageResource(imageUtils.getImageId());
        autoActivity.readAuto();
        teleopActivity.readTeleop();
        endActivity.readEnd();
    }

    public String getPath()
    {
        return path.getText().toString();
    }

}