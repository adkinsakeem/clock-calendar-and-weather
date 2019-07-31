package com.adkins.clock_calendar_and_weather;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.Helper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;


import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.graphics.drawable.Drawable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import android.support.v4.content.res.ResourcesCompat;

import static com.adkins.clock_calendar_and_weather.optionsMenu.BGSavedValue;


//import static com.adkins.clock_calendar_and_weather.R.id.menuicon;


public class MainActivity extends AppCompatActivity {

    static double pageWidth;
    static double pageHeight;
    static double sixCardsWidth = pageWidth / 8;
    static double sixCardsHeight = sixCardsWidth * 1.5;
    static double mainCardWidth = sixCardsWidth * 1.5;
    static double mainCardHeight = sixCardsHeight * 1.5;
    static double cardSpaces = (sixCardsWidth / 2) / 16;
    static double menuButtonSize = pageWidth / 12.8571;
    static double weatherIconWidth = pageWidth * .375;
    static double weatherIconHeight = (pageWidth * .375) * 1.333333;
    static double topAndBottomMargin = 1;
    Date currentTime = Calendar.getInstance().getTime();
   // Calendar currentTime = Calendar.getInstance();
    static String[] dateLetters = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    static int[] calendarCardID = {R.id.calendarcardsun, R.id.calendarcardmon,
            R.id.calendarcardtue, R.id.calendarcardwed, R.id.calendarcardthu,
            R.id.calendarcardfri, R.id.calendarcardsat};
    static int[] calendarCards = {R.drawable.calendarcardsun, R.drawable.calendarcardmon,
            R.drawable.calendarcardtue, R.drawable.calendarcardwed, R.drawable.calendarcardthu,
            R.drawable.calendarcardfri, R.drawable.calendarcardsat};
    // string date;
   // private static Label digitalClockDisplay;
    static String usedFontsAndroid = "digital-7.ttf#Digital-7";
    static String usedFontsiOS = "Digital-7";
    static String usedFontsWindows = "Assets/Fonts/digital-7.ttf#Digital-7";
    static double timeFontMultiplier = 1;
    private String WeatherApiKey = "76068897e190edfaf19250bc4c2e2968";
    static String apiZipCode = "23227";
    public static boolean menuOpen = false;
  //  ISharedPreferences prefs = PreferenceManager.GetDefaultSharedPreferences(Android.App.Application.Context);
   // RelativeLayout mainLayout = new RelativeLayout();
  //JFrame frame = new JFrame("MainLayout");
    RelativeLayout MainLayout;
   // static StackLayout DigitalClockStack;
    private static String[] BGNamesIndex = { "Beach", "Blank", "City", "Flowers", "Mountains", "NightCity", "Ocean", "Richmond", "Sky", "Water" };
    public static int[] BGNames = { R.drawable.beach, R.drawable.blank, R.drawable.city, R.drawable.flowers,
            R.drawable.mountains, R.drawable.nightcity, R.drawable.ocean,
            R.drawable.richmond, R.drawable.sky, R.drawable.water };
    protected static int BGIndex = 0;
    TextView[] dateView = new TextView[7];
    private double currentTopMargin = 0;
    private Locale locale;
    private String weatherZipCode = "23227";
    private String tempColor = "#ffffff";
    static ConstraintLayout constraintLayout;
    //private static LocalDate today = LocalDate.now();
   // private static ZoneId TZ = ZoneId.of("Pacific/Auckland");
    //public static GridBagLayout menuButton;
    //static Image[] BGImage = new Image[BGNames.Length];
   // static Grid BGLayers = new Grid();
    //Context context;
    public SharedPreferences appSettings;
    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        screenSizeCheck();

       // constraintLayout = (ConstraintLayout) findViewById(R.id.backgroundid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appSettings = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        /*ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.menuicon);
        mImageView.setImageResource(R.drawable.menuicon);*/
        MainPageLayout();

    }


    private void MainPageLayout()
    {
        //MainLayout = new RelativeLayout;


        setContentView(R.layout.activity_main);
        //MainLayout = (RelativeLayout)findViewById(R.id.backgroundid);
       MenuButton();
        getCalendarCards();
        getClockTime();
        WeatherAPICall();
      // ImageView layout = (ImageView) findViewById(R.id.beach);
       // constraintLayout.setBackgroundResource(R.drawable.beach);
       // constraintLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.beach) );
        //constraintLayout.setBackgroundResource(R.drawable.beach);
       // ImageView iv = constraintLayout.findViewById(R.id.backgroundid);
       // iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.beach));
        String tempBGIndex = appSettings.getString(BGSavedValue, "Blank");
        if(Arrays.asList(BGNamesIndex).contains(tempBGIndex)) {
            android.support.constraint.ConstraintLayout MainActivityBG = findViewById(R.id.backgroundid);
            MainActivityBG.setBackground(getResources().getDrawable(
                    BGNames[Arrays.asList(BGNamesIndex).indexOf(tempBGIndex)]));
        }

        System.out.println("Temp BG Index" + tempBGIndex);
        System.out.println("BG Index" + BGNames[Arrays.asList(BGNamesIndex).indexOf(tempBGIndex)]);
          /*  if (constraintLayout != null) {

                constraintLayout.setBackgroundResource(BGNames[Arrays.asList(BGNamesIndex).indexOf(tempBGIndex)]);
            }*/




/*

            for (int x = 0; x < BGNames.Length; x++)
    {
        BGImage[x] = setBackground(BGNames[x]);
        BGLayers.Children.Add(BGImage[x], 0, 0);
    }

            if (BGNames.Contains(Application.Current.Properties["BackgroundImage"].ToString()))
    changeBGOpacity();

    //addUsedFonts();
    //digitalClockDisplay.Font = new Font(usedFontsAndroid, 24, Font);

            mainLayout.Children.Add(BGLayers,
            Constraint.Constant(0),
            Constraint.Constant(0),
            Constraint.RelativeToParent((parent) => { return parent.Width; }),
            Constraint.RelativeToParent((parent) => { return parent.Height; }));



            mainLayout.Children.Add(MainLayout,
            Constraint.Constant(0),
            Constraint.Constant(0),
            Constraint.RelativeToParent((parent) => { return parent.Width; }),
            Constraint.RelativeToParent((parent) => { return parent.Height; }));

    Content = mainLayout;*/


}

  /*  @SuppressLint("MissingSuperCall")
    protected void onResume()
    {
        if(MainLayout != null)
            MainLayout.setBackgroundResource(BGNames[BGIndex]);

    }*/

 /* public void getBackground(){
      String tempBGIndex = appSettings.getString(BGSavedValue, "Blank");
      if(Arrays.asList(BGNamesIndex).contains(tempBGIndex)) {
          if (constraintLayout != null)
              constraintLayout.setBackgroundResource(BGNames[Arrays.asList(BGNamesIndex).indexOf(tempBGIndex)]);
      }

  }*/

  public static void update(String BGName){

      menuOpen = false;
      //getBackground(BGName);



  }

public void getBackground(String BGName){
    if(Arrays.asList(BGNamesIndex).contains(BGName)) {
        android.support.constraint.ConstraintLayout MainActivityBG = findViewById(R.id.backgroundid);
        MainActivityBG.setBackground(getResources().getDrawable(
                BGNames[Arrays.asList(BGNamesIndex).indexOf(BGName)]));
    }
}


    public void screenSizeCheck()
        {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            double pageWidth = metrics.widthPixels;
            double pageHeight = metrics.heightPixels;

            sixCardsWidth = pageWidth / 8;
            sixCardsHeight = sixCardsWidth * 1.5;
            mainCardWidth = sixCardsWidth * 1.5;
            mainCardHeight = sixCardsHeight * 1.5;
            cardSpaces = (sixCardsWidth / 2) / 8;
            menuButtonSize = pageWidth / 12.8571;
            weatherIconWidth = pageWidth * .375;
            weatherIconHeight = (pageWidth * .375) * 1.333333;
            topAndBottomMargin = (pageHeight /200);

        }

        private void MenuButton(){

         ImageView menuIcon =(ImageView) findViewById(R.id.menuicon);
            Drawable  menuIconDraw  = getResources().getDrawable(R.drawable.menuicon);
            menuIcon.setImageDrawable(menuIconDraw);
            menuIcon.getLayoutParams().height = (int) menuButtonSize;
            menuIcon.getLayoutParams().width = (int) menuButtonSize;
            menuIcon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event){
                if(menuOpen == false) {
                    menuOpen = true;
                    startActivity(new Intent(MainActivity.this, optionsMenu.class));
                }

                return true;
                }
            });

        }

        private void getCalendarCards(){
            int count = 0;
            double calandarCardFontSize = (sixCardsWidth * .5);
            double labelMargin;
            String BeginningDateString = "";
            String TodaysDateString = "";
            int dateIDs[] = {R.id.suncalendardate, R.id.moncalendardate, R.id.tuecalendardate,
                    R.id.wedcalendardate, R.id.thucalendardate, R.id.fricalendardate, R.id.satcalendardate};
            ImageView[] calendarImages = new ImageView[7];
            currentTopMargin += topAndBottomMargin + menuButtonSize;

            DateFormat df = new SimpleDateFormat("d", Locale.getDefault());
           Calendar cal = Calendar.getInstance();
            cal.setTime(currentTime);
            Date todaysDate = cal.getTime();
            TodaysDateString = df.format(todaysDate);
            TextView tv1;

            for(int x=0; x < 7; x++){

                cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
                cal.add(Calendar.DATE, x);
                Date firstDayOfTheWeek = cal.getTime();

                BeginningDateString = df.format(firstDayOfTheWeek);

                calendarImages[x] =(ImageView) findViewById(calendarCardID[x]);
                Drawable  calendarCardDraw  = getResources().getDrawable(calendarCards[x]);

                calendarImages[x].setImageResource(calendarCards[x]);
                calendarImages[x].setImageDrawable(calendarCardDraw);

                tv1 = (TextView)findViewById(dateIDs[x]);
                tv1.setText(BeginningDateString);
                tv1.setTextColor(Color.BLACK);

                if(BeginningDateString.equals(TodaysDateString)) {
                    calendarImages[x].getLayoutParams().height = (int) mainCardHeight;
                    calendarImages[x].getLayoutParams().width = (int) mainCardWidth;
                }else{
                    calendarImages[x].getLayoutParams().height = (int) sixCardsHeight;
                    calendarImages[x].getLayoutParams().width = (int) sixCardsWidth;

                }


            }

        }

        private void getClockTime(){
            TextClock DigitalClock = findViewById(R.id.digital_clock);

        }

        public void WeatherAPICall() {
            OkHttpClient weatherClient = new OkHttpClient();
            URL Weatherurl;
            String weatherURL = ("http://api.openweathermap.org/data/2.5/weather?zip="+
                    weatherZipCode+",us&APPID="+WeatherApiKey);
            Request weatherAPIRequest = new Request.Builder()
                    .url(weatherURL)
                    .build();

            weatherClient.newCall(weatherAPIRequest).enqueue((new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                       String weatherResponse = response.body().string();
                        OpenWeatherMap weatherObject = new Gson().fromJson(weatherResponse,OpenWeatherMap.class);

                        SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(currentTime);
                        Date todaysDate = cal.getTime();

                       // Helper http = new Helper();
                        Gson gson = new Gson();

                        Type mType = new TypeToken<OpenWeatherMap>(){}.getType();

                        Date sunriseTime = new Date((long) weatherObject.getSys().getSunrise() * 1000L);
                        Date sunsetTime = new Date((long) weatherObject.getSys().getSunset() * 1000L);

                        double tempPass = weatherObject.getMain().getTemp();
                        String iconPass = (weatherObject.getWeather().get(0).getMain()).toLowerCase();

                       if((todaysDate.compareTo(sunriseTime) >= 0) && (todaysDate.compareTo(sunsetTime) < 0)){
                                findDayTimeIcon(iconPass);
                            }else{
                                findNightTimeIcon(iconPass);
                           }

                        getTemperature(tempPass);
                    }
                }
            }));
        }

    public void findDayTimeIcon(String iconPass)
    {
      int WIcon;


        switch (iconPass)
        {
            case "clear sky":
                WIcon = R.drawable.clearskyday;
                break;
            case "few clouds":
                WIcon = R.drawable.fewcloudsday;
                break;
            case "scattered clouds":
                WIcon = R.drawable.scatteredclouds;
                break;
            case "broken clouds":
                WIcon = R.drawable.brokenclouds;
                break;
            case "shower rain":
                WIcon = R.drawable.showerrain;
                break;
            case "rain":
                WIcon = R.drawable.rainday;
                break;
            case "thunderstorm":
                WIcon = R.drawable.thunderstorm;
                break;
            case "snow":
                WIcon = R.drawable.snow;
                break;
            case "mist":
                WIcon = R.drawable.mist;
                break;
            default:
                WIcon = R.drawable.clearskyday;
                break;
        }

       getWeatherIcon(iconPass, WIcon);

    }

    protected void findNightTimeIcon(String iconPass)
    {
        int WIcon;

        switch (iconPass)
        {
            case "clear sky":
                WIcon = R.drawable.clearskynight;
                break;
            case "few clouds":
                WIcon = R.drawable.fewcloudsnight;
                break;
            case "scattered clouds":
                WIcon = R.drawable.scatteredclouds;
                break;
            case "broken clouds":
                WIcon = R.drawable.brokenclouds;
                break;
            case "shower rain":
                WIcon = R.drawable.showerrain;
                break;
            case "rain":
                WIcon = R.drawable.rainnight;
                break;
            case "thunderstorm":
                WIcon = R.drawable.thunderstorm;
                break;
            case "snow":
                WIcon = R.drawable.snow;
                break;
            case "mist":
                WIcon = R.drawable.mist;
                break;
                default:
                    WIcon = R.drawable.clearskynight;
                    break;
        }
       getWeatherIcon(iconPass, WIcon);

    }

    public void getWeatherIcon(String iconPass, final int WIcon){
        runOnUiThread(new Runnable() {
            public void run(){
            ImageView weatherIcon = (ImageView) findViewById(R.id.weathericon);
            Drawable weatherIconDraw = getResources().getDrawable(WIcon);
            System.out.println("ImageView: " + weatherIcon);
            weatherIcon.setImageDrawable(weatherIconDraw);
            //menuIcon.getLayoutParams().height = (int) menuButtonSize;
            // menuIcon.getLayoutParams().width = (int) menuButtonSize;
        }});
    }

    public void getTemperature(final double tempPass) {
        runOnUiThread(new Runnable() {
            public void run(){

            TextView menuIcon = findViewById(R.id.temperaturetext);

        menuIcon.setText(Math.round((tempPass -273.15)*(9/5)+32)+"°");
        menuIcon.setTextColor(Color.parseColor(tempColor));
        }});
    }

        //Declarations from the Weather API*******************************************
    //********************************************************************************

    public static class OpenWeatherMap {
        private List<Weather> weather;
        private String base;
        private  Main main;
        private Sys sys;
        private int id;
        private String name;
        private int cod;

        public  OpenWeatherMap() {
        }

        public  OpenWeatherMap(List<Weather> weatherList, String base, Main main, int dt, Sys sys, int id, String name, int cod) {
            this.weather = weatherList;
            this.main = main;
            this.sys = sys;
            this.id = id;
            this.name = name;
        }

        public  List<Weather> getWeather() {
            return weather;
        }

        public  void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public  Main getMain() {
            return main;
        }

        public  void setMain(Main main) {
            this.main = main;
        }


        public  Sys getSys() {
            return sys;
        }

        public  void setSys(Sys sys) {
            this.sys = sys;
        }

        public  int getId() {
            return id;
        }

        public  void setId(int id) {
            this.id = id;
        }

        public  String getName() {
            return name;
        }

        public  void setName(String name) {
            this.name = name;
        }
    }

    public class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public  Weather(int id, String main, String description, String icon) {
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }

        public  int getId() {
            return id;
        }

        public  void setId(int id) {
            this.id = id;
        }

        public  String getMain() {
            return main;
        }

        public  void setMain(String main) {
            this.main = main;
        }

        public  String getDescription() {
            return description;
        }

        public  void setDescription(String description) {
            this.description = description;
        }

        public  String getIcon() {
            return icon;
        }

        public  void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public class Sys {
        private int type;
        private int id;
        private double message;
        private String country;
        private double sunrise;
        private double sunset;

        public  Sys(int type, int id, double message, String country, double sunrise, double sunset) {
            this.type = type;
            this.id = id;
            this.message = message;
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public  int getType() {
            return type;
        }

        public  void setType(int type) {
            this.type = type;
        }

        public  int getId() {
            return id;
        }

        public  void setId(int id) {
            this.id = id;
        }

        public  double getMessage() {
            return message;
        }

        public  void setMessage(double message) {
            this.message = message;
        }

        public  String getCountry() {
            return country;
        }

        public  void setCountry(String country) {
            this.country = country;
        }

        public  double getSunrise() {
            return sunrise;
        }

        public  void setSunrise(double sunrise) {
            this.sunrise = sunrise;
        }

        public  double getSunset() {
            return sunset;
        }

        public  void setSunset(double sunset) {
            this.sunset = sunset;
        }
    }

    public class Main {
        private double temp;
        private double pressure;
        private int humidity;
        private double temp_min;
        private double temp_max;

        public  Main(double temp, double pressure, int humidity, double temp_min, double temp_max) {
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
        }

        public  double getTemp() {
            return temp;
        }

        public  void setTemp(double temp) {
            this.temp = temp;
        }

        public  double getPressure() {
            return pressure;
        }

        public  void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public  int getHumidity() {
            return humidity;
        }

        public  void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public  double getTemp_min() {
            return temp_min;
        }

        public  void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

        public  double getTemp_max() {
            return temp_max;
        }

        public  void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }
    }

    //****************************************************************************
    //****************************************************************************


}
