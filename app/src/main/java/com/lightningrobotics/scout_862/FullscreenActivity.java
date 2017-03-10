package com.lightningrobotics.scout_862;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;

import static com.lightningrobotics.scout_862.CycleData.makeCycleArrays;
import static com.lightningrobotics.scout_862.FileUtils.appData;
import static com.lightningrobotics.scout_862.FileUtils.matchCounter;
import static com.lightningrobotics.scout_862.PagerAdapter.fragmentArray;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener{

    FileUtils fileUtils = new FileUtils();
    ImageUtils imageUtils = new ImageUtils(this);
    AutoActivity autoActivity = new AutoActivity();
    TeleopActivity teleopActivity = new TeleopActivity();
    FileExplorer fileExplorer = new FileExplorer();
    EndActivity endActivity = new EndActivity();
    Button lastMatchButton;
    Button nextMatchButton;
    TextView allianceText;
    TextView allianceNumber;
    TextView matchNumber;
    TextView teamNumber;
    EditText scouterName;
    ImageView robotPic;
    TabLayout tabLayout;
    Button launchFileExplorer;
    String sheetPath;
    public static String PACKAGE_NAME;
    public static boolean importedData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);
        //Set Package name
        PACKAGE_NAME = getApplicationContext().getPackageName();

        lastMatchButton = (Button) findViewById(R.id.btn_previous_match);
        nextMatchButton = (Button) findViewById(R.id.btn_next_match);
        allianceText = (TextView) findViewById(R.id.tv_alliance_text);
        allianceNumber = (TextView) findViewById(R.id.tv_alliance_position);
        matchNumber = (TextView) findViewById(R.id.tv_match_number);
        teamNumber = (TextView) findViewById(R.id.tv_team_number);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        scouterName = (EditText) findViewById(R.id.et_scouter_name);
        robotPic = (ImageView) findViewById(R.id.iv_robot_picture);
        launchFileExplorer = (Button) findViewById(R.id.btn_getFile);
        lastMatchButton.setOnClickListener(this);
        nextMatchButton.setOnClickListener(this);
        allianceText.setOnClickListener(this);
        launchFileExplorer.setOnClickListener(this);

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

    public void onClick(View v) {
        if(v == launchFileExplorer)
        {
            DialogProperties properties = new DialogProperties();
            properties.selection_mode = DialogConfigs.SINGLE_MODE;
            properties.selection_type = DialogConfigs.FILE_SELECT;
            properties.root = new File(DialogConfigs.DEFAULT_DIR);
            properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
            properties.offset = new File(DialogConfigs.DEFAULT_DIR);
            properties.extensions = null;
            FilePickerDialog dialog = new FilePickerDialog(FullscreenActivity.this,properties);
            dialog.setTitle("Select a File");
            dialog.setDialogSelectionListener(new DialogSelectionListener() {
                @Override
                public void onSelectedFilePaths(String[] files) {
                    sheetPath = files[0];
                }
            });
            dialog.show();
        }

        if(v == allianceText)
        {
            File file = new File(sheetPath);
            if (file.exists()) {
                makeCycleArrays();
                System.out.println(sheetPath);
                importData();
            }
            else
                Toast.makeText(getApplicationContext(), sheetPath + " does not exist",
                        Toast.LENGTH_LONG).show();
            fileUtils.arrayToExcel(sheetPath);
        }

        if (importedData != false) {
            if ((v == nextMatchButton && matchCounter < 100) || (v == lastMatchButton && matchCounter > 1)) {
                //Write all your values to a 2D array
                writeMain();
                autoActivity.writeAuto();
                teleopActivity.writeTeleop();
                endActivity.writeEnd();

                //Copy that array to an excel sheet
                fileUtils.arrayToExcel(sheetPath);
                if (v == nextMatchButton)
                    matchCounter++;
                if (v == lastMatchButton)
                    matchCounter--;
                //Read the values from an array
                readMain();
                autoActivity.readAuto();
                teleopActivity.readTeleop();
                endActivity.readEnd();
            }
        } else {
            matchCounter = 1;
            Toast.makeText(getApplicationContext(), "Import Data by pressing \"Alliance\"",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void writeMain()
    {
        appData[1][matchCounter] = scouterName.getText().toString();
    }

    public void readMain()
    {
        matchNumber.setText(String.format("%01d",matchCounter));
        teamNumber.setText(appData[0][matchCounter]);
        scouterName.setText(appData[1][matchCounter]);
        //Set and get image
        File robotImage = imageUtils.getFile(appData[0][matchCounter]);
        if(robotImage == null)
            robotPic.setImageBitmap(imageUtils.getDefaultImage());
        else
            robotPic.setImageBitmap(BitmapFactory.decodeFile(robotImage.getAbsolutePath()));
    }

    public void importData()
    {
        //Set the "importedData" flag to true
        importedData = true;
        //copy data from the excel sheet to an array
        appData = fileUtils.excelToArray(sheetPath);
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
            default: tabLayout.setBackgroundColor(getResources().getColor(R.color.colorFirstCCseven));
                break;
        }
        //the value in the first collumn is for the current team
        teamNumber.setText(appData[0][matchCounter]);
        scouterName.setText(appData[1][matchCounter]);
        matchNumber.setText(String.format("%01d",matchCounter));
        //Set and get image
        File robotImage = imageUtils.getFile(appData[0][matchCounter]);
        if(robotImage == null)
            robotPic.setImageBitmap(imageUtils.getDefaultImage());
        else
            robotPic.setImageBitmap(BitmapFactory.decodeFile(robotImage.getAbsolutePath()));
        autoActivity.readAuto();
        teleopActivity.readTeleop();
        endActivity.readEnd();
    }
}