package com.adkins.clock_calendar_and_weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class optionsMenu extends Activity {

    private String[] BGNames = { "Beach", "Blank", "City", "Flowers", "Mountains", "NightCity", "Ocean", "Richmond", "Sky", "Water" };
    private static String[] usedFonts = { "rabelo_regular.ttf", "digital7.ttf", "bradley_gratis.ttf", "conduit2.ttf",
            "oldlondon.ttf", "summerfire.ttf" , "oaklandhills1991.ttf", "snowtopcaps.ttf", "hultogsnowdrift.ttf" };
    private String[] fontsName = {"Regular", "Digital" , "Gothic", "Bubble", "Old Time",
            "Fire 01", "Fire 02", "Ice 01", "Ice 02"};
    private String[] fontColorList = { "#7fffd4", "#f5f5dc", "#000000", "#0000FF", "#a52a2a", "#00ffff", "#00008b", "#a9a9a9", "#013220", "#8b008b", "#ff8c00", "#8b0000", "#228b22", "#ffd700", "#808080", "#008000", "#ff69b4", "#4b0082", "#967bb6", "#add8e6", "#90ee90", "#ffffed", "#00FF00", "#ff00ff", "#000080", "#ffa500", "#ffc0cb", "#800080", "#FF0000", "#87ceeb", "#ee82ee", "#FFFFFF", "#FFFF00" };
    private String[] fontColors = { "Aquamarine", "Beige", "Black", "Blue", "Brown", "Cyan", "Dark Blue", "Dark Gray", "Dark Green", "Dark Magenta", "Dark Orange", "Dark Red", "Forest Green", "Gold", "Gray", "Green", "Hot Pink", "Indigo", "Lavender", "Light Blue", "Light Green", "Light Yellow", "Lime Green", "Magenta", "Navy", "Orange", "Pink", "Purple", "Red", "Sky Blue", "Violet", "White", "Yellow" };
    public Spinner BGSpinner;
    public Spinner FontColorSpinner;
    public Spinner FontSpinner;
    public static final String SHARED_PREFS = "sharedPrefs";
    public SharedPreferences appSettings;
    public static final String BGSavedValue = "";
    public static final String fontColorSavedValue = "Black";
    public static final String fontSavedValue = "Digital";
    public static final String zipCodeValue = "23227";
    private Button okButton;
    private Button cancelButton;


    public static final String SWITCH1 = "switch1";


    public static int[] BGNamesDrawable = {R.drawable.beach, R.drawable.blank, R.drawable.city, R.drawable.flowers,
            R.drawable.mountains, R.drawable.nightcity, R.drawable.ocean,
            R.drawable.richmond, R.drawable.sky, R.drawable.water};
    protected static int BGIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuwindow);
        appSettings = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        backgroundImageList();
        fontColorList();
        fontList();
        getZipCode();
        buttonActions();
    }


    public void backgroundImageList() {
        String tempBG = "Blank";
        System.out.println("Hits Here");

        if(appSettings.contains(BGSavedValue))
          tempBG = appSettings.getString(BGSavedValue,"Blank");
      // tempBGIndex = appSettings.getInt(BGSavedValue, 1);

        BGSpinner = (Spinner) findViewById(R.id.backgroundList);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, BGNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BGSpinner.setAdapter(dataAdapter);

        if(Arrays.asList(BGNames).contains(tempBG))
            BGSpinner.setSelection(Arrays.asList(BGNames).indexOf(tempBG));
        else
            BGSpinner.setSelection(Arrays.asList(fontColors).indexOf("Blank"));

    }

    public void fontColorList() {
        String tempFontColor = "Black";

        if(appSettings.contains(fontColorSavedValue))
        tempFontColor = appSettings.getString(fontColorSavedValue, "Black");

        FontColorSpinner = (Spinner) findViewById(R.id.fontColorList);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fontColors);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FontColorSpinner.setAdapter(dataAdapter);


        if(Arrays.asList(fontColors).contains(tempFontColor))
            FontColorSpinner.setSelection(Arrays.asList(fontColors).indexOf(tempFontColor));
        else
            FontColorSpinner.setSelection(Arrays.asList(fontColors).indexOf("Black"));

    }


    public void fontList() {
        String tempFont = "Digital";

        if(appSettings.contains(fontSavedValue))
            tempFont = appSettings.getString(fontSavedValue, "Digital");

        FontSpinner = (Spinner) findViewById(R.id.textFontList);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fontsName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FontSpinner.setAdapter(dataAdapter);


        if(Arrays.asList(fontsName).contains(tempFont))
            FontSpinner.setSelection(Arrays.asList(fontsName).indexOf(tempFont));
        else
            FontSpinner.setSelection(Arrays.asList(fontsName).indexOf("Digital"));

    }

    public void getZipCode() {
        String tempZipCode = "23227";

        //if(appSettings.contains(zipCodeValue))
            //tempZipCode = appSettings.getString(zipCodeValue, "23227");

        EditText zipEdit = (EditText)findViewById(R.id.zipCodeTextBox);
        zipEdit.setText(tempZipCode);
    }

    public void buttonActions(){
        okButton = findViewById(R.id.OKButton);
        cancelButton = findViewById(R.id.CancelButton);
        okButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                System.out.println("checkSpinnerValue: "+ BGSpinner.getSelectedItem().toString());
                saveData();
                System.out.println("checkBGValue: "+ appSettings.getString(BGSavedValue, "Blank"));
                MainActivity.update(appSettings.getString(BGSavedValue, "Blank"),
                                    appSettings.getString(fontColorSavedValue, "Black"),
                                    appSettings.getString(fontSavedValue, "Digital"));

                finish();
                return true;
            }
        });

        cancelButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                finish();
                MainActivity.menuOpen = false;
                return true;
            }
        });

    }

/*
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.OKButton:
                saveData();
                dismiss();
                finish();
            case R.id.CancelButton:

        }
    }*/


    public void saveData(){
        SharedPreferences.Editor editor = appSettings.edit();

        //***Saving Background Data*******************************************
    editor.putString(BGSavedValue, BGSpinner.getSelectedItem().toString());
    //************************************************************************

    //***Saving Font Color Data*******************************************
    editor.putString(fontColorSavedValue, FontColorSpinner.getSelectedItem().toString());
    //************************************************************************

    //***Saving Fonts Data*******************************************
    editor.putString(fontSavedValue, FontSpinner.getSelectedItem().toString());
    //***************************************************************

    //***Saving Fonts Data*******************************************
    editor.putString(zipCodeValue, (findViewById(R.id.zipCodeTextBox).toString()));
    //***************************************************************
editor.commit();


}




        }
