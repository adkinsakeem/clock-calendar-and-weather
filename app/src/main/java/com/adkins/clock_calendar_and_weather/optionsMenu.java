package com.adkins.clock_calendar_and_weather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
        ConstraintLayout constraintLayout = findViewById(R.id.menuid);
        TextView menuTitle =  findViewById(R.id.menuTitle);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)menuTitle.getLayoutParams();
        params.bottomMargin = (int) (params.bottomMargin * .8);
        menuTitle.setTextSize((float) (48 * .8));
        menuTitle.setLayoutParams(params);
        backgroundImageList();
        fontColorList();
        fontList();
        getZipCode();
        buttonActions();
        layoutSizes();
    }


    public void backgroundImageList() {
        String tempBG = "Blank";

        if(appSettings.contains(BGSavedValue))
          tempBG = appSettings.getString(BGSavedValue,"Blank");

        BGSpinner = (Spinner) findViewById(R.id.backgroundList);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)BGSpinner.getLayoutParams();
        params.bottomMargin = (int) (params.bottomMargin * .8);
        params.height = (int) (params.height * .8);

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
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)FontColorSpinner.getLayoutParams();
        params.height = (int) (params.height * .8);

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
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)FontSpinner.getLayoutParams();
        params.height = (int) (params.height * .8);

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

        if(appSettings.contains(zipCodeValue))
            tempZipCode = appSettings.getString(zipCodeValue, "23227");

        EditText zipEdit = (EditText)findViewById(R.id.zipCodeTextBox);
        zipEdit.setText(tempZipCode);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)zipEdit.getLayoutParams();
        params.bottomMargin = (int) (params.bottomMargin * .8);


    }

    public void buttonActions(){
        okButton = findViewById(R.id.OKButton);
        cancelButton = findViewById(R.id.CancelButton);
        okButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                System.out.println("checkSpinnerValue: "+ BGSpinner.getSelectedItem().toString());
                saveData();
                MainActivity.update(appSettings.getString(BGSavedValue, "Blank"),
                                    appSettings.getString(fontColorSavedValue, "Black"),
                                    appSettings.getString(fontSavedValue, "Digital"),
                                    appSettings.getString(zipCodeValue, "23227"));

                finish();
                return true;
            }
        });

        cancelButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                finish();
                MainActivity.menuOpen = false;
                MainActivity.runWeatherAPI = false;
                return true;
            }
        });

    }


    public void layoutSizes(){
        View DividerBGtoMenu = (View) findViewById(R.id.menuBGDivider);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)DividerBGtoMenu.getLayoutParams();
        params.bottomMargin = (int) (params.bottomMargin * .8);
        params.topMargin = (int) (params.topMargin * .8);

        View DividerFonttoBG = (View) findViewById(R.id.BGFontColorDivider);
        params = (ConstraintLayout.LayoutParams)DividerFonttoBG.getLayoutParams();
        params.bottomMargin = (int) (params.bottomMargin * .8);
        params.topMargin = (int) (params.topMargin * .8);

        View DividerColortoText = (View) findViewById(R.id.fontColorTextDivider);
        params = (ConstraintLayout.LayoutParams)DividerColortoText.getLayoutParams();
        params.bottomMargin = (int) (params.bottomMargin * .8);
        params.topMargin = (int) (params.topMargin * .8);

        View DividerZiptoText = (View) findViewById(R.id.textFontZipDivider);
        params = (ConstraintLayout.LayoutParams)DividerZiptoText.getLayoutParams();
        params.bottomMargin = (int) (24 * .8);
        params.topMargin = (int) (24 * .8);

        TextView BGImageTitle = (TextView) findViewById(R.id.backgroundColorText);
        BGImageTitle.setTextSize((float) (24 * .8));

        TextView fontColorTitle = (TextView) findViewById(R.id.fontColorText);
        fontColorTitle.setTextSize((float) (24 * .8));

        TextView fontTitle = (TextView) findViewById(R.id.chooseFontText);
        fontTitle.setTextSize((float) (24 * .8));

        TextView zipCodeTitle = (TextView) findViewById(R.id.chooseZipCode);
        zipCodeTitle.setTextSize((float) (24 * .8));

        TextView cancelB = (TextView) findViewById(R.id.CancelButton);
        params = (ConstraintLayout.LayoutParams)cancelB.getLayoutParams();
        params.topMargin = (int) (params.topMargin * .8);

        TextView OkB = (TextView) findViewById(R.id.OKButton);
        params = (ConstraintLayout.LayoutParams)OkB.getLayoutParams();
        params.topMargin = (int) (params.topMargin * .8);

    }

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

    //***Saving Zip Code*******************************************
        TextView tempzip = findViewById(R.id.zipCodeTextBox);
    editor.putString(zipCodeValue, (tempzip.getText().toString()));
    //***************************************************************
editor.commit();


    }
}